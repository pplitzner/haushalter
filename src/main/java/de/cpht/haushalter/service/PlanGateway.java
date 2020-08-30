package de.cpht.haushalter.service;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.exception.PlanNotFoundException;

import java.util.List;

public interface PlanGateway {
    public List<Plan> showAllPlans();

    public Plan getPlanById(Long id);

    public Long startPlan(String title, String description);

    public void deletePlan(Long id);

    public void updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException;
}
