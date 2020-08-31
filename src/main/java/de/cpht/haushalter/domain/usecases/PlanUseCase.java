package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotFoundException;

import java.util.List;

public interface PlanUseCase {
    List<Plan> showAllPlans();
    Plan getPlanById(Long id) throws PlanNotFoundException;
    Long startPlan(String title, String description);
    void deletePlan(Long id) throws PlanNotFoundException;
    void updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException;
    void finishPlan(Long id) throws PlanNotFoundException;

    List<PlanItem> getItems(Long planId) throws PlanNotFoundException;
    Long addItem(Long planId, PlanItem item) throws PlanNotFoundException;
    void updateItem(Long id, PlanItem item) throws PlanItemNotFoundException;
    void deleteItem(Long id) throws PlanItemNotFoundException;


}
