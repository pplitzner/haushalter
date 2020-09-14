package de.cpht.haushalter.adapters.db.jpa;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanItemRepository;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanRepository;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.PlanFinishedException;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotDefaultException;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return planRepository.findAll().stream().map(plan -> plan.dto()).collect(Collectors.toList());
    }

    @Override
    public Plan getPlanById(Long id) {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return plan.dto();
    }

    @Override
    public Plan startDefaultPlan(String title, String description) {
        JpaPlan plan = createJpaPlan(title, description);
        plan.setDefault(true);
        return planRepository.save(plan).dto();
    }

    @Override
    public Plan startPlan(String title, String description) {
        JpaPlan plan = createJpaPlan(title, description);
        return planRepository.save(plan).dto();
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
        return planRepository.save(plan).dto();
    }

    @Override
    public void finishPlan(Long id) throws PlanNotFoundException {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        plan.setDone(true);
        planRepository.save(plan);
    }

    @Override
    public List<PlanItem> getItems(Long id) throws PlanNotFoundException{
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return itemRepository.findByPlan(plan).stream().map(item->item.dto()).collect(Collectors.toList());
    }

    @Override
    public PlanItem addItem(Long id, PlanItem item) throws PlanNotFoundException{
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        JpaPlanItem jpaItem = new JpaPlanItem();
        jpaItem.setTitle(item.title);
        jpaItem.setDescription(item.description);
        jpaItem.setPlan(plan);
        return itemRepository.save(jpaItem).dto();
    }

    @Override
    public PlanItem updateItem(Long id, PlanItem item) throws PlanNotFoundException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        jpaItem.update(item);
        return itemRepository.save(jpaItem).dto();
    }

    @Override
    public void deleteItem(Long id) throws PlanItemNotFoundException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        itemRepository.delete(jpaItem);
    }

    @Override
    public void checkItem(Long id) throws PlanItemNotFoundException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        jpaItem.setChecked(true);
        itemRepository.save(jpaItem);
    }

    @Override
    public void uncheckItem(Long id) throws PlanItemNotFoundException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        jpaItem.setChecked(false);
        itemRepository.save(jpaItem);
    }

    @Override
    public Plan makePlanFromDefault(Long defaultPlanId) throws PlanNotFoundException, PlanNotDefaultException {
        JpaPlan defaultPlan = planRepository.findById(defaultPlanId).orElseThrow(() -> new PlanNotFoundException(defaultPlanId));
        if(!defaultPlan.isDefault()){
            throw new PlanNotDefaultException(defaultPlanId);
        }
        JpaPlan jpaPlan = planRepository.save(createJpaPlan(defaultPlan.getTitle(), defaultPlan.getDescription()));
        getItems(defaultPlan.getId()).stream().forEach(item->addItem(jpaPlan.getId(), item));
        return planRepository.save(jpaPlan).dto();
    }

    private JpaPlan createJpaPlan(String title, String description) {
        JpaPlan plan = new JpaPlan();
        plan.setTitle(title);
        plan.setDescription(description);
        return plan;
    }
}
