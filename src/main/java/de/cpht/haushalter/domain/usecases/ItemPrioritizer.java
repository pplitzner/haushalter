package de.cpht.haushalter.domain.usecases;

import de.cpht.haushalter.domain.entities.ItemPriority;
import de.cpht.haushalter.domain.entities.ItemType;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class ItemPrioritizer {

    public static void setPriority(PlanUseCase planUseCase){
        planUseCase.getItemsByType(ItemType.TIMED)
                .forEach(planItem -> {
                    planItem.priority = getPriority(planItem.checkedAt, planItem.timeInterval);
                    planUseCase.updateItem(planItem.id, planItem);
                });
    }

    // TODO does not have to be public (check test accessing this method)
    public static ItemPriority getPriority(LocalDateTime checkedAt, Period timeInterval){

        if(checkedAt==null || timeInterval==null){
            // initial case
            return ItemPriority.PERFECT_TIME;
        }
        final long timeSinceLastDone = ChronoUnit.DAYS.between(checkedAt, LocalDateTime.now());
        final long timeAfterOneInterval = ChronoUnit.DAYS.between(checkedAt, checkedAt.plus(timeInterval));

        float halfInterval = Math.round(timeAfterOneInterval / 2f);
        int perfectInterval = Math.round(timeAfterOneInterval * .9f);
        if(timeSinceLastDone<halfInterval){
            return ItemPriority.NOT_AVAILABLE;
        }
        if(timeSinceLastDone>=halfInterval && timeSinceLastDone<perfectInterval){
            return ItemPriority.CAN_BE_DONE;
        }
        if(timeSinceLastDone>=perfectInterval && timeSinceLastDone<=timeAfterOneInterval){
            return ItemPriority.PERFECT_TIME;
        }
        return ItemPriority.RED_ALERT;
    }
}
