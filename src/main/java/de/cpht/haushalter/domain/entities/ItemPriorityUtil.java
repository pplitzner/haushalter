package de.cpht.haushalter.domain.entities;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class ItemPriorityUtil {

    public enum ItemPriority{
        NOT_AVAILABLE, CAN_BE_DONE, PERFECT_TIME, RED_ALERT
    }

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
