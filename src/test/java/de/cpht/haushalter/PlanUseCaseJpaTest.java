package de.cpht.haushalter;

import de.cpht.haushalter.adapters.db.jpa.repository.PlanItemRepository;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.PlanFinishedException;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PlanUseCaseJpaTest {

    @Autowired
    private PlanUseCase planUseCase;
    @Autowired
    PlanItemRepository itemRepository;

    @Test
    public void testStartPlanShowAllPlans(){
        assertTrue(planUseCase.showAllPlans().isEmpty());
        planUseCase.startPlan("Test Title", "Test description");
        assertEquals(1, planUseCase.showAllPlans().size());
    }

    @Test
    public void testDeletePlan(){
        Plan plan = planUseCase.startPlan("Test Title", "Test description");
        assertEquals(1, planUseCase.showAllPlans().size());
        planUseCase.deletePlan(plan.id);
        assertTrue(planUseCase.showAllPlans().isEmpty());
    }

    @Test
    public void testDeletePlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.deletePlan(1L));
    }

    @Test
    public void testGetPlanById(){
        Plan plan = planUseCase.startPlan("Test Title", "Test description");
        Plan planById = planUseCase.getPlanById(plan.id);
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
        Plan plan = planUseCase.startPlan("Test Title1", "Test description1");
        Plan updatedPlan = new Plan();
        updatedPlan.title = "Test Title2";
        updatedPlan.description = "Test description2";
        planUseCase.updatePlan(plan.id, updatedPlan);
        Plan planById = planUseCase.getPlanById(plan.id);
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
    public void testUpdatePlanFinishedException() {
        Plan plan = planUseCase.startPlan("t", "d");
        planUseCase.finishPlan(plan.id);
        assertThrows(PlanFinishedException.class, ()->planUseCase.updatePlan(plan.id, new Plan()));
    }

    @Test
    public void testFinishPlan(){
        Plan plan = planUseCase.startPlan("t", "d");
        Plan planById = planUseCase.getPlanById(plan.id);
        assertFalse(planById.done);
        planUseCase.finishPlan(plan.id);
        planById = planUseCase.getPlanById(plan.id);
        assertTrue(planById.done);
    }

    @Test
    public void testFinishPlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.finishPlan(1L));
    }

    @Test
    public void testGetItemsAddItem(){
        Plan plan = planUseCase.startPlan("t", "d");
        List<PlanItem> items = planUseCase.getItems(plan.id);
        assertTrue(items.isEmpty());

        PlanItem item = new PlanItem("it", "id");
        planUseCase.addItem(plan.id, item);
        items = planUseCase.getItems(plan.id);
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
        Plan plan = planUseCase.startPlan("t", "d");
        PlanItem item = new PlanItem("it", "id");
        Long itemId = planUseCase.addItem(plan.id, item);
        assertFalse(planUseCase.getItems(plan.id).isEmpty());
        planUseCase.deleteItem(itemId);
        assertTrue(planUseCase.getItems(plan.id).isEmpty());
    }

    @Test
    public void testDeletePlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.deleteItem(1L));
    }

    @Test
    public void testUpdateItem(){
        Plan plan = planUseCase.startPlan("t", "d");
        Long itemId = planUseCase.addItem(plan.id, new PlanItem("it", "id"));
        PlanItem item2 = new PlanItem("item title2", "item description2");
        planUseCase.updateItem(itemId, item2);
        PlanItem updatedItem = itemRepository.findById(itemId).get().dto();
        assertEquals(itemId, updatedItem.id);
        assertEquals(item2.title, updatedItem.title);
        assertEquals(item2.description, updatedItem.description);
    }

    @Test
    public void testUpdatePlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.updateItem(1L, new PlanItem()));
    }

    @Test
    public void testCheckItem(){
        Long itemId = planUseCase.addItem(planUseCase.startPlan("t", "d").id, new PlanItem("it", "id"));
        assertFalse(itemRepository.findById(itemId).get().dto().checked);
        planUseCase.checkItem(itemId);
        assertTrue(itemRepository.findById(itemId).get().dto().checked);
        planUseCase.uncheckItem(itemId);
        assertFalse(itemRepository.findById(itemId).get().dto().checked);
    }

    @Test
    public void testCheckItemPlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.checkItem(1L));
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.uncheckItem(1L));
    }

}