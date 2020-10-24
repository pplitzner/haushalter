package de.cpht.haushalter.adapters.db.jpa;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanItemRepository;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanRepository;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.entities.PlanType;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.PlanFinishedException;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotDefaultException;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanUseCaseJpaImpl implements PlanUseCase {

    @Autowired
    PlanRepository planRepository;
    @Autowired
    PlanItemRepository itemRepository;

    @Override
    public List<Plan> showAllPlans() {
        return planRepository.findByType(PlanType.CHECKLIST).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public List<Plan> showAllPlans(PlanType type) {
        return planRepository.findByType(type).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public List<Plan> showDefaultPlans() {
        return planRepository.findByIsDefault(true).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public List<Plan> showNonDefaultPlans() {
        return planRepository.findByIsDefault(false).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public Plan getPlanById(Long id) {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return DtoMapper.dtoFrom(plan);
    }

    @Override
    public Plan startPlan(String title, String description) {
        return startPlan(title, description, PlanType.CHECKLIST);
    }

    @Override
    public Plan startPlan(String title, String description, PlanType type) {
        JpaPlan plan = createJpaPlan(title, description, type);
        return DtoMapper.dtoFrom(planRepository.save(plan));
    }

    @Override
    public void deletePlan(Long id) {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        planRepository.delete(plan);
    }

    @Override
    public Plan updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException, PlanFinishedException {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        if(plan.isDone()){
            throw new PlanFinishedException(plan.getId());
        }
        plan.update(updatedPlan);
        return DtoMapper.dtoFrom(planRepository.save(plan));
    }

    @Override
    public void toggleDone(Long id) throws PlanNotFoundException {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        plan.setDone(!plan.isDone());
        planRepository.save(plan);
    }

    @Override
    public List<PlanItem> getCheckedItems() {
        return itemRepository.findByIsChecked(true).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public List<PlanItem> getItems(Long id) throws PlanNotFoundException{
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return itemRepository.findByPlan(plan).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public PlanItem addItem(Long id, PlanItem item) throws PlanNotFoundException{
        JpaPlanItem jpaItem = DtoMapper.jpaItemFrom(item);
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        jpaItem.setPlan(plan);
        return DtoMapper.dtoFrom(itemRepository.save(jpaItem));
    }

    @Override
    public PlanItem updateItem(Long id, PlanItem item) throws PlanNotFoundException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        jpaItem.update(item);
        return DtoMapper.dtoFrom(itemRepository.save(jpaItem));
    }

    @Override
    public void deleteItem(Long id) throws PlanItemNotFoundException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        itemRepository.delete(jpaItem);
    }

    @Override
    public void toggleCheck(Long id) throws PlanItemNotFoundException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        boolean checkedBefore = jpaItem.isChecked();
        jpaItem.setChecked(!checkedBefore);
        jpaItem.setCheckedAt(jpaItem.getCheckedAt()==null?LocalDateTime.now():null);
        itemRepository.save(jpaItem);
    }

    @Override
    public Plan makePlanFromDefault(Long defaultPlanId) throws PlanNotFoundException, PlanNotDefaultException {
        JpaPlan defaultPlan = planRepository.findById(defaultPlanId).orElseThrow(() -> new PlanNotFoundException(defaultPlanId));
        if(!defaultPlan.getType().equals(PlanType.DEFAULT)){
            throw new PlanNotDefaultException(defaultPlanId);
        }
        JpaPlan jpaPlan = planRepository.save(createJpaPlan(defaultPlan.getTitle(), defaultPlan.getDescription(), PlanType.CHECKLIST));
        getItems(defaultPlan.getId()).forEach(item->addItem(jpaPlan.getId(), item));
        return DtoMapper.dtoFrom(planRepository.save(jpaPlan));
    }

    @Override
    public Plan startPlanForRemainingItems(Long id, String title, String description) throws PlanNotFoundException {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        plan.setDone(true);
        planRepository.save(plan);
        JpaPlan remainingItemsPlan = planRepository.save(createJpaPlan(title, description, PlanType.CHECKLIST));
        getItems(plan.getId()).stream()
                .filter(item->!item.checked)
                .forEach(uncheckedItem->addItem(remainingItemsPlan.getId(), uncheckedItem));
        return DtoMapper.dtoFrom(planRepository.save(remainingItemsPlan));
    }

    private JpaPlan createJpaPlan(String title, String description, PlanType type) {
        JpaPlan plan = new JpaPlan();
        plan.setTitle(title);
        plan.setDescription(description);
        plan.setType(type);
        return plan;
    }
}
