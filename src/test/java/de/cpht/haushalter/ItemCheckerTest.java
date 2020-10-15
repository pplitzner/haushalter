package de.cpht.haushalter;

import de.cpht.haushalter.domain.ItemChecker;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemCheckerTest {

    @Autowired
    private PlanUseCase planUseCase;

    @Autowired
    private ItemChecker itemChecker;

    @Test
    public void testCheckItems(){
        final Plan plan = planUseCase.startPlan("", "");
        final PlanItem itemToUncheck = new PlanItem("Has to be done again", null);
        itemToUncheck.startDate = LocalDate.now().minusWeeks(1).minusDays(1);
        itemToUncheck.timeInterval = Period.ofWeeks(1);
        itemToUncheck.checked = true;
        planUseCase.addItem(plan.id, itemToUncheck);

        final PlanItem itemNotToUncheck = new PlanItem("Still Time to do", null);
        itemNotToUncheck.startDate = LocalDate.now().minusWeeks(1);
        itemNotToUncheck.timeInterval = Period.ofWeeks(2);
        itemNotToUncheck.checked = true;
        planUseCase.addItem(plan.id, itemNotToUncheck);

        final List<PlanItem> checkedItems = planUseCase.getCheckedItems();
        assertEquals(2, checkedItems.size());
        assertTrue(checkedItems.contains(itemToUncheck));
        assertTrue(checkedItems.contains(itemNotToUncheck));

        itemChecker.checkItems();

        final List<PlanItem> checkedItemsAfterItemChecker = planUseCase.getCheckedItems();
        assertEquals(1, checkedItemsAfterItemChecker.size());
        assertFalse(checkedItemsAfterItemChecker.contains(itemToUncheck));
        assertTrue(checkedItemsAfterItemChecker.contains(itemNotToUncheck));
    }
}