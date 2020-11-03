package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.ItemType;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.entities.PlanType;
import de.cpht.haushalter.exception.*;

import java.time.Period;
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
    List<PlanItem> getItemsByType(ItemType type);
    List<PlanItem> getItemsByTimeInterval(Period timeInterval);
    List<PlanItem> getDaylies();
    List<PlanItem> getNotDaylies();
    List<PlanItem> getItems(Long planId) throws PlanNotFoundException;
    PlanItem addItem(Long planId, PlanItem item) throws PlanNotFoundException;
    PlanItem updateItem(Long id, PlanItem item) throws PlanItemNotFoundException;
    void deleteItem(Long id) throws PlanItemNotFoundException;
    void checkItem(Long id) throws PlanItemNotFoundException, TimedItemNotFinishableException;
}
