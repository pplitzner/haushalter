package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.exception.PlanFinishedException;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotFoundException;

import java.util.List;

public interface PlanUseCase {
    List<Plan> showAllPlans();
    Plan getPlanById(Long id) throws PlanNotFoundException;
    Plan startPlan(String title, String description);
    Plan startDefaultPlan(String title, String description);
    void deletePlan(Long id) throws PlanNotFoundException;
    Plan updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException, PlanFinishedException;
    void finishPlan(Long id) throws PlanNotFoundException;

    List<PlanItem> getItems(Long planId) throws PlanNotFoundException;
    PlanItem addItem(Long planId, PlanItem item) throws PlanNotFoundException;
    PlanItem updateItem(Long id, PlanItem item) throws PlanItemNotFoundException;
    void deleteItem(Long id) throws PlanItemNotFoundException;
    void checkItem(Long id) throws  PlanItemNotFoundException;
    void uncheckItem(Long id) throws  PlanItemNotFoundException;


}
