package de.cpht.haushalter.controller;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanRESTController {

    @Autowired
    private PlanUseCase planUseCase;

    @GetMapping
    public List<Plan> index(){
        return planUseCase.showAllPlans();
    }

    @PostMapping
    public Plan store(@RequestBody Plan plan) {
        return planUseCase.startPlan(plan.title, plan.description);
    }

    @GetMapping("/{id}")
    public Plan show(@PathVariable Long id){
        return planUseCase.getPlanById(id);
    }

    @PostMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Plan updatedPlan){
        planUseCase.updatePlan(id, updatedPlan);
    }

    @PostMapping("/{id}/done")
    public void finishPlan(@PathVariable Long id){
        planUseCase.finishPlan(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        planUseCase.deletePlan(id);
    }
}
