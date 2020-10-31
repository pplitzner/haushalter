package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.entities.PlanType;
import de.cpht.haushalter.exception.PlanFinishedException;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotDefaultException;
import de.cpht.haushalter.exception.PlanNotFoundException;

import java.util.List;

public interface PlanUseCase {

    List<Plan> showAllPlans(PlanType type);
    Plan getPlanById(Long id) throws PlanNotFoundException;
    Plan startPlan(String title, String description, PlanType type);
    void deletePlan(Long id) throws PlanNotFoundException;
    Plan updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException, PlanFinishedException;
    Plan finishPlan(Long id, Boolean startPlanForRemainingItems) throws PlanNotFoundException, PlanFinishedException;
    boolean checkPlanDone(Long planId);
    Plan startPlanFromDefault(Long defaultPlan) throws PlanNotFoundException, PlanNotDefaultException;
    Plan startPlanForRemainingItems(Long id) throws PlanNotDefaultException;

    /**
     * Get all items of all plans which are done/checked.
     * @return a list of checked items
     */
    List<PlanItem> getCheckedItems();
    /**
     * Get all items of all plans which are not done/checked and which can be done again.
     * @return a list of items to do
     */
    List<PlanItem> getTodos();
    List<PlanItem> getItems(Long planId) throws PlanNotFoundException;
    PlanItem addItem(Long planId, PlanItem item) throws PlanNotFoundException;
    PlanItem updateItem(Long id, PlanItem item) throws PlanItemNotFoundException;
    void deleteItem(Long id) throws PlanItemNotFoundException;
    // TODO do not allow timed items to be unchecked; only untimed checklist items can be toggled
    void toggleCheck(Long id) throws  PlanItemNotFoundException;
}
