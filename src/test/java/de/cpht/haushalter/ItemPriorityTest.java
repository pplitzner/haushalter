package de.cpht.haushalter;


import de.cpht.haushalter.domain.entities.ItemPriorityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ItemPriorityTest {

    @Test
    public void testItemPriorityWeekly(){
        assertEquals(ItemPriorityUtil.ItemPriority.PERFECT_TIME, ItemPriorityUtil.getPriority(null, null));
        LocalDateTime now = LocalDateTime.now();
        // weekly items
        Period timeInterval = Period.ofWeeks(1);

        LocalDateTime checkedAt = now.minusDays(8);
        assertEquals(ItemPriorityUtil.ItemPriority.RED_ALERT, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(7).plusMinutes(1);
        assertEquals(ItemPriorityUtil.ItemPriority.PERFECT_TIME, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(6);
        assertEquals(ItemPriorityUtil.ItemPriority.PERFECT_TIME, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(5);
        assertEquals(ItemPriorityUtil.ItemPriority.CAN_BE_DONE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(4);
        assertEquals(ItemPriorityUtil.ItemPriority.CAN_BE_DONE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(3);
        assertEquals(ItemPriorityUtil.ItemPriority.NOT_AVAILABLE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(2);
        assertEquals(ItemPriorityUtil.ItemPriority.NOT_AVAILABLE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(1);
        assertEquals(ItemPriorityUtil.ItemPriority.NOT_AVAILABLE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusDays(0);
        assertEquals(ItemPriorityUtil.ItemPriority.NOT_AVAILABLE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));
    }

    @Test
    public void testItemPriorityMonthly(){
        assertEquals(ItemPriorityUtil.ItemPriority.PERFECT_TIME, ItemPriorityUtil.getPriority(null, null));
        LocalDateTime now = LocalDateTime.now();
        // weekly items
        Period timeInterval = Period.ofMonths(1);

        LocalDateTime checkedAt = now.minusWeeks(5);
        assertEquals(ItemPriorityUtil.ItemPriority.RED_ALERT, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusMonths(1).plusDays(1);
        assertEquals(ItemPriorityUtil.ItemPriority.PERFECT_TIME, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusMonths(1).plusDays(3);
        assertEquals(ItemPriorityUtil.ItemPriority.PERFECT_TIME, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusWeeks(3);
        assertEquals(ItemPriorityUtil.ItemPriority.CAN_BE_DONE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusWeeks(2);
        assertEquals(ItemPriorityUtil.ItemPriority.NOT_AVAILABLE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusWeeks(1);
        assertEquals(ItemPriorityUtil.ItemPriority.NOT_AVAILABLE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

        checkedAt = now.minusWeeks(0);
        assertEquals(ItemPriorityUtil.ItemPriority.NOT_AVAILABLE, ItemPriorityUtil.getPriority(checkedAt, timeInterval));

    }

}
