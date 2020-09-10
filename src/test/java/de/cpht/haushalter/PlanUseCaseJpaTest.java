package de.cpht.haushalter;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PlanUseCaseJpaTest {

    @Autowired
    private PlanUseCase planUseCase;

    @Test
    public void testStartPlanShowAllPlans(){
        assertTrue(planUseCase.showAllPlans().isEmpty());
        planUseCase.startPlan("Test Title", "Test description");
        assertEquals(1, planUseCase.showAllPlans().size());
    }

    @Test
    public void testDeletePlan(){
        Long plan = planUseCase.startPlan("Test Title", "Test description");
        assertEquals(1, planUseCase.showAllPlans().size());
        planUseCase.deletePlan(plan);
        assertTrue(planUseCase.showAllPlans().isEmpty());
    }

    @Test
    public void testDeletePlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.deletePlan(1L));
    }

    @Test
    public void testGetPlanById(){
        Long plan = planUseCase.startPlan("Test Title", "Test description");
        Plan planById = planUseCase.getPlanById(plan);
        assertNotNull(planById);
        assertEquals("Test Title", planById.title);
        assertEquals("Test description", planById.description);
    }

    @Test
    public void testGetPlanByIdNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.getPlanById(1L));
    }

    @Test
    public void testUpdatePlan(){
        Long plan = planUseCase.startPlan("Test Title1", "Test description1");
        Plan updatedPlan = new Plan();
        updatedPlan.title = "Test Title2";
        updatedPlan.description = "Test description2";
        planUseCase.updatePlan(plan, updatedPlan);
        Plan planById = planUseCase.getPlanById(plan);
        assertNotNull(planById);
        assertEquals(plan, planById.id);
        assertEquals(updatedPlan.title, planById.title);
        assertEquals(updatedPlan.description, planById.description);
    }

    @Test
    public void testUpdatePlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.updatePlan(1L, new Plan()));
    }

    @Test
    public void testFinishPlan(){
        Long plan = planUseCase.startPlan("t", "d");
        Plan planById = planUseCase.getPlanById(plan);
        assertFalse(planById.done);
        planUseCase.finishPlan(plan);
        planById = planUseCase.getPlanById(plan);
        assertTrue(planById.done);
    }

    @Test
    public void testFinishPlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.finishPlan(1L));
    }

    @Test
    public void testGetItemsAddItem(){
        Long plan = planUseCase.startPlan("t", "d");
        List<PlanItem> items = planUseCase.getItems(plan);
        assertTrue(items.isEmpty());

        PlanItem item = new PlanItem();
        item.title = "item title";
        item.description = "item description";
        planUseCase.addItem(plan, item);
        items = planUseCase.getItems(plan);
        assertFalse(items.isEmpty());
        PlanItem next = items.iterator().next();
        assertEquals("item title", next.title);
        assertEquals("item description", next.description);
    }

    @Test
    public void testAddItemPlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.getItems(1L));
        assertThrows(PlanNotFoundException.class, ()->planUseCase.addItem(1L, new PlanItem()));
    }

    @Test
    public void testDeleteItem(){
        Long plan = planUseCase.startPlan("t", "d");
        PlanItem item = new PlanItem();
        item.title = "item title";
        item.description = "item description";
        Long itemId = planUseCase.addItem(plan, item);
        assertFalse(planUseCase.getItems(plan).isEmpty());
        planUseCase.deleteItem(itemId);
        assertTrue(planUseCase.getItems(plan).isEmpty());
    }

    @Test
    public void testDeletePlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.deleteItem(1L));
    }

    @Test
    public void testUpdateItem(){
        Long plan = planUseCase.startPlan("t", "d");
        PlanItem item = new PlanItem();
        item.title = "item title";
        item.description = "item description";
        Long itemId = planUseCase.addItem(plan, item);
        PlanItem item2 = new PlanItem();
        item2.title = "item title2";
        item2.description = "item description2";
        planUseCase.updateItem(itemId, item2);
        PlanItem updatedItem = planUseCase.getItems(plan).iterator().next();
        assertEquals(itemId, updatedItem.id);
        assertEquals(item2.title, updatedItem.title);
        assertEquals(item2.description, updatedItem.description);
    }

    @Test
    public void testUpdatePlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.updateItem(1L, new PlanItem()));
    }
}
