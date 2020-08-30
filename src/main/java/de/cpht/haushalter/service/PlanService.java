package de.cpht.haushalter.service;

import de.cpht.haushalter.domain.entities.PlanDTO;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.adapters.repository.PlanRepository;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService implements PlanUseCase {

    @Autowired
    PlanRepository repository;

    @Override
    public List<Plan> showAllPlans() {
        return repository.findAll();
    }

    @Override
    public Plan getPlanById(Long id) {
        return repository.findById(id).orElseThrow(()-> new PlanNotFoundException(id));
    }

    @Override
    public Long startPlan(String title, String description) {
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setDescription(description);
        return repository.save(plan).getId();
    }

    @Override
    public void deletePlan(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updatePlan(Long id, PlanDTO updatedPlan) throws PlanNotFoundException {
        Plan plan = repository.findById(id).orElseThrow(() -> new PlanNotFoundException(id));
        plan.update(updatedPlan);
        repository.save(plan);
    }
}
