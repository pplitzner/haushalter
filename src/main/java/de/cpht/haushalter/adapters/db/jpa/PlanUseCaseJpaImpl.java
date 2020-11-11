package de.cpht.haushalter.adapters.db.jpa;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanItemRepository;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanRepository;
import de.cpht.haushalter.domain.entities.*;
import de.cpht.haushalter.domain.usecases.ItemPrioritizer;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanUseCaseJpaImpl implements PlanUseCase {

    @Autowired
    PlanRepository planRepository;
    @Autowired
    PlanItemRepository itemRepository;

    @Override
    public List<Plan> showAllPlans(PlanType type) {
        return planRepository.findByTypeAndDoneFalseOrderByTitle(type).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public Plan getPlanById(Long id) {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return DtoMapper.dtoFrom(plan);
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
    public Plan finishPlan(Long id, Boolean startPlanForRemainingItems) throws PlanNotFoundException {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        if(plan.isDone()){
            throw new PlanFinishedException(plan.getId());
        }
        plan.setDone(true);
        planRepository.save(plan);
        if(startPlanForRemainingItems){
            return startPlanForRemainingItems(plan.getId());
        }
        return null;
    }

    @Override
    public List<PlanItem> getItemsByType(ItemType type) {
        return itemRepository.findByType(type).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public List<PlanItem> getItemsByTimeInterval(Period timeInterval) {
        return itemRepository.findByTimeInterval(timeInterval).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
    }

    @Override
    public List<PlanItem> getDaily() {
        return getItemsByTimeInterval(Period.ofDays(1));
    }

    @Override
    public List<PlanItem> getNotDaily() {
        return itemRepository.findByTimeIntervalNot(Period.ofDays(1)).stream().map(DtoMapper::dtoFrom).collect(Collectors.toList());
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
        jpaItem.setPlanTitle(plan.getTitle());
        if(plan.getType().equals(PlanType.TIMEDLIST)){
            jpaItem.setType(ItemType.TIMED);
        }
        else{
            jpaItem.setType(ItemType.DEFAULT);
        }
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
    public void checkItem(Long id) throws PlanItemNotFoundException, TimedItemNotFinishableException {
        JpaPlanItem jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        if(jpaItem.getType().equals(ItemType.DEFAULT)){
            jpaItem.setCheckedAt(jpaItem.getCheckedAt()==null?LocalDateTime.now():null);
        }
        else if(jpaItem.getType().equals(ItemType.TIMED)){
            if(jpaItem.getPriority()!=null && jpaItem.getPriority().equals(ItemPriority.NOT_AVAILABLE)){
                throw new TimedItemNotFinishableException(jpaItem.getId());
            }
            jpaItem.setCheckedAt(LocalDateTime.now());
            jpaItem.setPriority(ItemPrioritizer.getPriority(jpaItem.getCheckedAt(), jpaItem.getTimeInterval()));
        }
        itemRepository.save(jpaItem);
    }

    @Override
    public Plan startPlanFromDefault(Long defaultPlanId) throws PlanNotFoundException, PlanNotDefaultException {
        JpaPlan defaultPlan = planRepository.findById(defaultPlanId).orElseThrow(() -> new PlanNotFoundException(defaultPlanId));
        if(!defaultPlan.getType().equals(PlanType.DEFAULT)){
            throw new PlanNotDefaultException(defaultPlanId);
        }
        JpaPlan jpaPlan = planRepository.save(createJpaPlan(defaultPlan.getTitle(), defaultPlan.getDescription(), PlanType.CHECKLIST));
        getItems(defaultPlan.getId()).forEach(item->addItem(jpaPlan.getId(), item));
        return DtoMapper.dtoFrom(planRepository.save(jpaPlan));
    }

    @Override
    public Plan startPlanForRemainingItems(Long id) throws PlanNotFoundException {
        JpaPlan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        plan.setDone(true);
        planRepository.save(plan);
        JpaPlan remainingItemsPlan = planRepository.save(createJpaPlan(plan.getTitle(), plan.getDescription(), plan.getType()));
        getItems(plan.getId()).stream()
                .filter(item->item.checkedAt==null)
                .forEach(uncheckedItem->addItem(remainingItemsPlan.getId(), uncheckedItem));
        return DtoMapper.dtoFrom(planRepository.save(remainingItemsPlan));
    }

    @Override
    public boolean checkPlanDone(Long planId) {
        return getItems(planId).stream().allMatch(planItem -> planItem.checkedAt!=null);
    }

    private JpaPlan createJpaPlan(String title, String description, PlanType type) {
        JpaPlan plan = new JpaPlan();
        plan.setTitle(title);
        plan.setDescription(description);
        plan.setType(type);
        return plan;
    }
}
