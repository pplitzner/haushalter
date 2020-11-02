package de.cpht.haushalter;

import de.cpht.haushalter.domain.entities.ItemPriority;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.entities.PlanType;
import de.cpht.haushalter.domain.usecases.ItemPrioritizer;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class ItemPriorityIntegrationTest {

    @Autowired
    PlanUseCase planUseCase;

    @Test
    public void testItemPriority(){
        final Plan plan = planUseCase.startPlan("", "", PlanType.TIMEDLIST);
        final PlanItem planItem = planUseCase.addItem(plan.id, new PlanItem("", ""));
        planItem.timeInterval = Period.ofMonths(1);
        planItem.checkedAt = LocalDateTime.now().minusMonths(1).plusDays(1);
        planUseCase.updateItem(planItem.id, planItem);

        final PlanItem updatedItem = planUseCase.getItems(plan.id).iterator().next();
        assertNull(updatedItem.priority);

        ItemPrioritizer.setPriority(planUseCase);

        final PlanItem prioritySetItem = planUseCase.getItems(plan.id).iterator().next();
        assertNotNull(prioritySetItem.priority);
        assertEquals(ItemPriority.PERFECT_TIME, prioritySetItem.priority);

    }
}
