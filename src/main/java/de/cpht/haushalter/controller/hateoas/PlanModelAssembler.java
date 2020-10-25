package de.cpht.haushalter.controller.hateoas;

import de.cpht.haushalter.controller.PlanRESTController;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlanModelAssembler implements RepresentationModelAssembler<Plan, EntityModel<Plan>> {

    @Override
    public EntityModel<Plan> toModel(Plan plan) {
        return EntityModel.of(plan,
                linkTo(methodOn(PlanRESTController.class).show(plan.id)).withSelfRel(),
                linkTo(methodOn(PlanRESTController.class).index(PlanType.CHECKLIST)).withRel("plans (checklist)"));
    }

}
