package de.cpht.haushalter.controller;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanType;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanRESTController {

    @Autowired
    private PlanUseCase planUseCase;

    @GetMapping()
    public List<Plan> index(@RequestParam PlanType type){
        return planUseCase.showAllPlans(type);
    }

    @PostMapping
    public Plan store(@RequestBody Plan plan, @RequestParam PlanType type) {
        // TODO redundant plan type passed
        return planUseCase.startPlan(plan.title, plan.description, type);
    }

    @GetMapping("/{id}")
    public Plan show(@PathVariable Long id){
        return planUseCase.getPlanById(id);
    }

    @PutMapping("/{id}")
    public Plan update(@PathVariable Long id, @RequestBody Plan updatedPlan){
        return planUseCase.updatePlan(id, updatedPlan);
    }

    @PostMapping("/{id}/finishPlan")
    public Plan finishPlan(@PathVariable Long id, @Nullable Boolean startPlanForRemainingItems){
        if(startPlanForRemainingItems){
            return planUseCase.startPlanForRemainingItems(id);
        }
        return planUseCase.finishPlan(id, startPlanForRemainingItems);
    }

    @GetMapping("/{id}/checkPlanDone")
    public Boolean checkDonePlan(@PathVariable Long id){
        return planUseCase.checkPlanDone(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        planUseCase.deletePlan(id);
    }
}
