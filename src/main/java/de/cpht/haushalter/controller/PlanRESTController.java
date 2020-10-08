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

    @PutMapping("/{id}")
    public Plan update(@PathVariable Long id, @RequestBody Plan updatedPlan){
        return planUseCase.updatePlan(id, updatedPlan);
    }

    @PostMapping("/{id}/toggleDone")
    public void finishPlan(@PathVariable Long id){
        planUseCase.toggleDone(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        planUseCase.deletePlan(id);
    }
}
