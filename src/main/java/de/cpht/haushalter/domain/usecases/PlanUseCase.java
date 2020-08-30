package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.PlanDTO;
import de.cpht.haushalter.exception.PlanNotFoundException;

import java.util.List;

public interface PlanUseCase {
    List<PlanDTO> showAllPlans();
    PlanDTO getPlanById(Long id) throws PlanNotFoundException;
    Long startPlan(String title, String description);
    void deletePlan(Long id) throws PlanNotFoundException;
    void updatePlan(Long id, PlanDTO updatedPlan) throws PlanNotFoundException;
}
