package de.cpht.haushalter.controller;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/plans")
public class PlanController {
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

    @PostMapping
    public String store(Plan plan) {
        planRepository.save(plan);
        return "redirect:/plans";
    }
}
