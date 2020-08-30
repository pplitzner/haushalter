package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.exception.PlanNotFoundException;

import java.util.List;

public interface PlanUseCase {
    List<Plan> showAllPlans();
    Plan getPlanById(Long id) throws PlanNotFoundException;
    Long startPlan(String title, String description);
    void deletePlan(Long id) throws PlanNotFoundException;
    void updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException;
}
