package de.cpht.haushalter.controller;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/plans")
public class PlanController {
    private PlanRepository planRepository;

    @Autowired
    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listPlans(Model model){
        model.addAttribute("plans", planRepository.findAll());
        return "plans";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPlan(Plan plan) {
        planRepository.save(plan);
        return "redirect:/plans";
    }
}
