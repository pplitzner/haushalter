package de.cpht.haushalter.controller;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.model.PlanItem;
import de.cpht.haushalter.repository.PlanItemRepository;
import de.cpht.haushalter.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/plans")
public class PlanController {
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("plans", planRepository.findAll());
        return "plans";
    }

    @GetMapping("/{planId}")
    public String show(@PathVariable Long planId, Model model){
        model.addAttribute("plan", planRepository.getOne(planId));
        return "plan";
    }

    @PostMapping("/{planId}")
    public String update(Plan plan){
        return "plans";
    }

    @PostMapping
    public String store(Plan plan) {
        planRepository.save(plan);
        return "redirect:/plans";
    }
}
