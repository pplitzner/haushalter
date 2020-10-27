package de.cpht.haushalter;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.entities.PlanType;
import de.cpht.haushalter.domain.usecases.ItemChecker;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.service.ItemCheckerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemCheckerTest {

    @MockBean
    ItemCheckerService itemCheckerService;

    @Autowired
    private PlanUseCase planUseCase;

    @Test
    public void testCheckItems(){
        final Plan plan = planUseCase.startPlan("", "", PlanType.CHECKLIST);
        final PlanItem itemToUncheck = new PlanItem("Has to be done again", null);
        itemToUncheck.startDate = LocalDateTime.now().minusDays(1);
        itemToUncheck.timeInterval = Period.ofDays(1);
        itemToUncheck.checkedAt = LocalDateTime.now();
        planUseCase.addItem(plan.id, itemToUncheck);

        final PlanItem itemNotToUncheck = new PlanItem("Still Time to do", null);
        itemNotToUncheck.startDate = LocalDateTime.now().minusWeeks(1);
        itemNotToUncheck.timeInterval = Period.ofWeeks(2);
        itemNotToUncheck.checkedAt = LocalDateTime.now();
        planUseCase.addItem(plan.id, itemNotToUncheck);

        final List<PlanItem> checkedItems = planUseCase.getCheckedItems();
        assertEquals(2, checkedItems.size());
        assertTrue(checkedItems.contains(itemToUncheck));
        assertTrue(checkedItems.contains(itemNotToUncheck));

        ItemChecker.checkItems(planUseCase);

        final List<PlanItem> checkedItemsAfterItemChecker = planUseCase.getCheckedItems();
        assertEquals(1, checkedItemsAfterItemChecker.size());
        assertFalse(checkedItemsAfterItemChecker.contains(itemToUncheck));
        assertTrue(checkedItemsAfterItemChecker.contains(itemNotToUncheck));
    }
}
