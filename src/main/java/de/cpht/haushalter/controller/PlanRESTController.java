package de.cpht.haushalter.controller;

import de.cpht.haushalter.domain.entities.PlanDTO;
import de.cpht.haushalter.adapters.repository.Plan;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanRESTController {

    @Autowired
    private PlanUseCase planUseCase;

    @GetMapping
    public List<PlanDTO> index(Model model){
        return planUseCase.showAllPlans();
    }

    @PostMapping
    public Long store(@RequestBody PlanDTO planDTO) {
        return planUseCase.startPlan(planDTO.title, planDTO.description);
    }

    @GetMapping("/{id}")
    public PlanDTO show(@PathVariable Long id){
        return planUseCase.getPlanById(id);
    }

    @PostMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody PlanDTO updatedPlan){
        planUseCase.updatePlan(id, updatedPlan);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        planUseCase.deletePlan(id);
    }
}
