package de.cpht.haushalter.adapters.db.jpa;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaPlanGateway implements PlanUseCase {

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
        PlanJpaEntity plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return plan.dto();
    }

    @Override
    public Long startPlan(String title, String description) {
        PlanJpaEntity plan = new PlanJpaEntity();
        plan.setTitle(title);
        plan.setDescription(description);
        return planRepository.save(plan).getId();
    }

    @Override
    public void deletePlan(Long id) {
        PlanJpaEntity plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        planRepository.delete(plan);
    }

    @Override
    public void updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException {
        PlanJpaEntity plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        plan.update(updatedPlan);
        planRepository.save(plan);
    }

    @Override
    public List<PlanItem> getItems(Long id) throws PlanNotFoundException{
        PlanJpaEntity plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return itemRepository.findByPlan(plan).stream().map(item->item.dto()).collect(Collectors.toList());
    }

    @Override
    public Long addItem(Long id, PlanItem item) throws PlanNotFoundException{
        PlanJpaEntity plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        PlanItemJpaEntity jpaItem = new PlanItemJpaEntity();
        jpaItem.setTitle(item.title);
        jpaItem.setDescription(item.description);
        jpaItem.setPlan(plan);
        return itemRepository.save(jpaItem).getId();
    }

    @Override
    public void updateItem(Long id, PlanItem item) throws PlanNotFoundException {
        PlanItemJpaEntity jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        jpaItem.update(item);
        itemRepository.save(jpaItem);
    }

    @Override
    public void deleteItem(Long id) throws PlanItemNotFoundException {
        PlanItemJpaEntity jpaItem = itemRepository.findById(id).orElseThrow(() -> new PlanItemNotFoundException(id));
        itemRepository.delete(jpaItem);
    }
}
