package de.cpht.haushalter.controller;

import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("plans/{planId}/items")
public class PlanItemRESTController {

    @Autowired
    private PlanUseCase planUseCase;

    @GetMapping
    public List<PlanItem> index(@PathVariable Long planId){
        return planUseCase.getItems(planId);
    }

    @PostMapping
    public PlanItem store(@PathVariable Long planId, @RequestBody PlanItem item) {
        return planUseCase.addItem(planId, item);
    }

    @PutMapping("{id}")
    public PlanItem update(@PathVariable Long id, @RequestBody PlanItem updatedItem){
        return planUseCase.updateItem(id, updatedItem);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        planUseCase.deleteItem(id);
    }

    @PostMapping("{id}/toggleCheck")
    public void checkItem(@PathVariable Long id){
        planUseCase.checkItem(id);
    }
}
