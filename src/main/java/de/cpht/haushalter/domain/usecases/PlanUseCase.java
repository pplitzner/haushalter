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
    @Deprecated
    /**
     * @deprecated type should be set, TODO remove
     */
    List<Plan> showAllPlans();
    List<Plan> showAllPlans(PlanType type);

    @Deprecated
    /**
     * FIXME remove
     */
    List<Plan> showDefaultPlans();

    @Deprecated
    /**
     * FIXME remove
     */
    List<Plan> showNonDefaultPlans();
    Plan getPlanById(Long id) throws PlanNotFoundException;
    Plan startPlan(String title, String description, PlanType type);
    @Deprecated
    /**
     * @deprecated type should be set during creation, see {@link #startPlan(String, String, PlanType)}
     * TODO remove
     */
    Plan startPlan(String title, String description);
    void deletePlan(Long id) throws PlanNotFoundException;
    Plan updatePlan(Long id, Plan updatedPlan) throws PlanNotFoundException, PlanFinishedException;
    void toggleDone(Long id) throws PlanNotFoundException;
    Plan makePlanFromDefault(Long defaultPlan) throws PlanNotFoundException, PlanNotDefaultException;
    Plan startPlanForRemainingItems(Long id, String title, String description) throws PlanNotDefaultException;

    /**
     * Get all items of all plans with checked set to true
     * @return a list of checked items
     */
    List<PlanItem> getCheckedItems();
    List<PlanItem> getItems(Long planId) throws PlanNotFoundException;
    PlanItem addItem(Long planId, PlanItem item) throws PlanNotFoundException;
    PlanItem updateItem(Long id, PlanItem item) throws PlanItemNotFoundException;
    void deleteItem(Long id) throws PlanItemNotFoundException;
    void toggleCheck(Long id) throws  PlanItemNotFoundException;
}
