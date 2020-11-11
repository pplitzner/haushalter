package de.cpht.haushalter.controller;

import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemRESTController {

    @Autowired
    private PlanUseCase planUseCase;

    @GetMapping("daily")
    public List<PlanItem> getDaily(){
        return planUseCase.getDaily();
    }

    @GetMapping("notDaily")
    public List<PlanItem> getNotDaily(){
        return planUseCase.getNotDaily();
    }

}
