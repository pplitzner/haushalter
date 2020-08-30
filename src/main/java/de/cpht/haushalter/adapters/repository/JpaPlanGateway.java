package de.cpht.haushalter.adapters.repository;

import de.cpht.haushalter.domain.entities.PlanDTO;
import de.cpht.haushalter.exception.PlanNotFoundException;
import de.cpht.haushalter.service.PlanGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaPlanGateway implements PlanGateway {

    @Autowired
    PlanRepository planRepository;

    @Override
    public List<PlanDTO> showAllPlans() {
        return planRepository.findAll().stream().map(plan -> plan.dto()).collect(Collectors.toList());
    }

    @Override
    public PlanDTO getPlanById(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        return plan.dto();
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
