package de.cpht.haushalter.domain;

import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ItemChecker {

    @Autowired
    private PlanUseCase planUseCase;

    /**
     * Check all items based on start date and interval.
     * Set checked flag and new start date accordingly.
     */
    public void checkItems(){
        planUseCase.getCheckedItems().stream()
                .filter(this::hasToBeDoneAgain)
                .forEach(this::adjustDateAndChecked);
    }

    private void adjustDateAndChecked(PlanItem item){
        planUseCase.toggleCheck(item.id);

        item.startDate = item.startDate.plus(item.timeInterval);
        planUseCase.updateItem(item.id, item);
    }

    private boolean hasToBeDoneAgain(PlanItem item) {
        return item.startDate.until(LocalDate.now()).getDays() > item.timeInterval.getDays();
    }

}
