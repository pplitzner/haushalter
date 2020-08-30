package de.cpht.haushalter.adapters.repository;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanDTO;
import de.cpht.haushalter.exception.PlanNotFoundException;
import de.cpht.haushalter.service.PlanGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JpaPlanGateway implements PlanGateway {

    @Autowired
    PlanRepository planRepository;

    @Override
    public List<Plan> showAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public Plan getPlanById(Long id) {
        return planRepository.findById(id).orElseThrow(()-> new PlanNotFoundException(id));
    }

    @Override
    public Long startPlan(String title, String description) {
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setDescription(description);
        return planRepository.save(plan).getId();
    }

    @Override
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

    @Override
    public void updatePlan(Long id, PlanDTO updatedPlan) throws PlanNotFoundException {
        Plan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        plan.update(updatedPlan);
        planRepository.save(plan);
    }
}
