package de.cpht.haushalter.controller;

import de.cpht.haushalter.domain.entities.ItemType;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemRESTController {

    @Autowired
    private PlanUseCase planUseCase;

    @GetMapping
    public List<PlanItem> getItemsByType(@RequestParam ItemType type){
        return planUseCase.getItemsByType(type);
    }

    @GetMapping
    public List<PlanItem> getItemsByTimeInterval(@RequestParam Period timeInterval){
        return planUseCase.getItemsByTimeInterval(timeInterval);
    }

}
