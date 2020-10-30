package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.PlanItem;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ItemChecker {

    private static final float REDO_FACTOR = 0.9f;

    /**
     * Check all items based on start date and interval.
     * Set checked flag and new start date accordingly.
     */
    public static void checkItems(PlanUseCase planUseCase){
        planUseCase.getCheckedItems().stream()
                .filter(item -> hasToBeDoneAgain(item))
                .forEach(item -> adjustDateAndChecked(item, planUseCase));
    }

    private static void adjustDateAndChecked(PlanItem item, PlanUseCase planUseCase){
        planUseCase.toggleCheck(item.id);

        item.startDate = item.startDate.plus(item.timeInterval);
        planUseCase.updateItem(item.id, item);
    }

    private static boolean hasToBeDoneAgain(PlanItem item) {
        return item.startDate.until(LocalDateTime.now(), ChronoUnit.DAYS) >= REDO_FACTOR*item.timeInterval.getDays();
    }

}
