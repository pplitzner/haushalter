package de.cpht.haushalter.service;

import de.cpht.haushalter.domain.entities.PlanDTO;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService implements PlanUseCase {

    @Autowired
    PlanGateway planGateway;

    @Override
    public List<PlanDTO> showAllPlans() {
        return planGateway.showAllPlans();
    }

    @Override
    public PlanDTO getPlanById(Long id) throws PlanNotFoundException {
        return planGateway.getPlanById(id);
    }

    @Override
    public Long startPlan(String title, String description) {
        return planGateway.startPlan(title, description);
    }

    @Override
    public void deletePlan(Long id) throws PlanNotFoundException {
        planGateway.deletePlan(id);
    }

    @Override
    public void updatePlan(Long id, PlanDTO updatedPlan) throws PlanNotFoundException {
        planGateway.updatePlan(id, updatedPlan);
    }
}
