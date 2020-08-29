package de.cpht.haushalter.controller;

import de.cpht.haushalter.exception.PlanNotFoundException;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanRESTController {

    @Autowired
    private PlanRepository planRepository;

    @GetMapping
    public List<Plan> index(Model model){
        return planRepository.findAll();
    }

    @PostMapping
    public Plan store(@RequestBody Plan plan) {
        return planRepository.save(plan);
    }

    @GetMapping("/{id}")
    public Plan show(@PathVariable Long id){
        return planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException(id));
    }

    @PostMapping("/{id}")
    public Plan update(@PathVariable Long id, @RequestBody Plan updatedPlan){
        return planRepository.findById(id).map(
                plan -> {
                    plan.setTitle(updatedPlan.getTitle());
                    plan.setDescription(updatedPlan.getDescription());
                    return planRepository.save(plan);
                })
                .orElseThrow(() -> new PlanNotFoundException(id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        planRepository.deleteById(id);
    }
}
