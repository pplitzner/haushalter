package de.cpht.haushalter.controller;

import de.cpht.haushalter.controller.hateoas.PlanModelAssembler;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/plans")
public class PlanRESTController {

    @Autowired
    private PlanUseCase planUseCase;

    @Autowired
    private PlanModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Plan>> index(){
        List<EntityModel<Plan>> plans = planUseCase.showAllPlans().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(plans,
                linkTo(methodOn(PlanRESTController.class).index()).withSelfRel());

    }

    @PostMapping
    public Long store(@RequestBody Plan plan) {
        return planUseCase.startPlan(plan.title, plan.description);
    }

    @GetMapping("/{id}")
    public EntityModel<Plan> show(@PathVariable Long id){
        Plan plan = planUseCase.getPlanById(id);
        return assembler.toModel(plan);
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
