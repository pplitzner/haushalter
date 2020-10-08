package de.cpht.haushalter;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.PlanFinishedException;
import de.cpht.haushalter.exception.PlanItemNotFoundException;
import de.cpht.haushalter.exception.PlanNotDefaultException;
import de.cpht.haushalter.exception.PlanNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PlanUseCaseTest {

    @Autowired
    private PlanUseCase planUseCase;

    @Test
    public void testStartPlanShowAllPlans(){
        assertTrue(planUseCase.showAllPlans().isEmpty());
        Plan plan = planUseCase.startPlan("Test Title", "Test description");
        List<Plan> plans = planUseCase.showAllPlans();
        assertEquals(1, plans.size());
        assertFalse(plan.done);
        assertFalse(plan.isDefault);
    }

    @Test
    public void testStartDefaultPlan(){
        Plan plan = planUseCase.startDefaultPlan("t", "d");
        Plan planById = planUseCase.getPlanById(plan.id);
        assertFalse(planById.done);
        assertTrue(planById.isDefault);
    }

    @Test
    public void testShowDefaultPlans(){
        final Plan defaultPlan = planUseCase.startDefaultPlan("D1", "DD1");
        final Plan defaultPlan2 = planUseCase.startDefaultPlan("D2", "DD2");
        planUseCase.startPlan("","");
        planUseCase.startPlan("","");
        final List<Plan> defaultPlans = planUseCase.showDefaultPlans();
        assertEquals(2, defaultPlans.size());
        assertTrue(defaultPlans.stream().noneMatch(plan->plan.title.equals("")));
        assertTrue(defaultPlans.stream().noneMatch(plan->plan.description.equals("")));
        assertTrue(defaultPlans.stream().anyMatch(plan->plan.title.equals(defaultPlan.title)));
        assertTrue(defaultPlans.stream().anyMatch(plan->plan.description.equals(defaultPlan.description)));
        assertTrue(defaultPlans.stream().anyMatch(plan->plan.title.equals(defaultPlan2.title)));
        assertTrue(defaultPlans.stream().anyMatch(plan->plan.description.equals(defaultPlan2.description)));
    }

    @Test
    public void testShowNonDefaultPlans(){
        planUseCase.startDefaultPlan("", "");
        planUseCase.startDefaultPlan("", "");
        final Plan nonDefaultPlan = planUseCase.startPlan("ND1","NDD1");
        final Plan nonDefaultPlan2 = planUseCase.startPlan("ND2","NDD2");
        final List<Plan> nonDefaultPlans = planUseCase.showNonDefaultPlans();
        assertEquals(2, nonDefaultPlans.size());
        assertTrue(nonDefaultPlans.stream().noneMatch(plan->plan.title.equals("")));
        assertTrue(nonDefaultPlans.stream().noneMatch(plan->plan.description.equals("")));
        assertTrue(nonDefaultPlans.stream().anyMatch(plan->plan.title.equals(nonDefaultPlan.title)));
        assertTrue(nonDefaultPlans.stream().anyMatch(plan->plan.description.equals(nonDefaultPlan.description)));
        assertTrue(nonDefaultPlans.stream().anyMatch(plan->plan.title.equals(nonDefaultPlan2.title)));
        assertTrue(nonDefaultPlans.stream().anyMatch(plan->plan.description.equals(nonDefaultPlan2.description)));
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
        updatedPlan.done = true;
        updatedPlan.isDefault = true;

        planUseCase.updatePlan(plan.id, updatedPlan);
        Plan planById = planUseCase.getPlanById(plan.id);
        assertNotNull(planById);
        assertEquals(plan.id, planById.id);
        assertEquals(updatedPlan.title, planById.title);
        assertEquals(updatedPlan.description, planById.description);
        assertEquals(updatedPlan.done, planById.done);
        assertEquals(updatedPlan.isDefault, planById.isDefault);
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
        assertEquals(item.title, next.title);
        assertEquals(item.description, next.description);
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
        Long itemId = planUseCase.addItem(plan.id, item).id;
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
        Long itemId = planUseCase.addItem(plan.id, new PlanItem("it", "id")).id;
        PlanItem item = new PlanItem("item title2", "item description2");
        item.startDate = LocalDate.now();
        item.timeInterval = Period.ofDays(14);
        item.duration = Duration.ofMinutes(10);

        planUseCase.updateItem(itemId, item);

        PlanItem updatedItem = planUseCase.getItems(plan.id).iterator().next();
        assertEquals(itemId, updatedItem.id);
        assertEquals(item.title, updatedItem.title);
        assertEquals(item.description, updatedItem.description);
        assertEquals(item.startDate, updatedItem.startDate);
        assertEquals(item.timeInterval, updatedItem.timeInterval);
        assertEquals(item.duration, updatedItem.duration);
    }

    @Test
    public void testUpdatePlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.updateItem(1L, new PlanItem()));
    }

    @Test
    public void testCheckItem(){
        Plan plan = planUseCase.startPlan("t", "d");
        Long itemId = planUseCase.addItem(plan.id, new PlanItem("it", "id")).id;
        PlanItem item = planUseCase.getItems(plan.id).iterator().next();
        assertFalse(item.checked);
        planUseCase.checkItem(itemId);
        item = planUseCase.getItems(plan.id).iterator().next();
        assertTrue(item.checked);
        planUseCase.uncheckItem(itemId);
        item = planUseCase.getItems(plan.id).iterator().next();
        assertFalse(item.checked);
    }

    @Test
    public void testCheckItemPlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.checkItem(1L));
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.uncheckItem(1L));
    }

    @Test
    public void testMakePlanFromDefault(){
        Plan defaultPlan = planUseCase.startDefaultPlan("Default", "plan");
        planUseCase.addItem(defaultPlan.id, new PlanItem("Item1", "Description1"));
        planUseCase.addItem(defaultPlan.id, new PlanItem("Item2", "Description2"));
        Plan plan = planUseCase.makePlanFromDefault(defaultPlan.id);
        assertEquals(defaultPlan.title, plan.title);
        assertEquals(defaultPlan.description, plan.description);
        assertNotEquals(defaultPlan.id, plan.id);
        assertFalse(plan.isDefault);

        assertEquals(planUseCase.getItems(defaultPlan.id).iterator().next(), planUseCase.getItems(plan.id).iterator().next());
    }

    @Test
    public void testMakePlanFromDefaultPlanNotDefaultException(){
        Plan plan = planUseCase.startPlan("", "");
        assertThrows(PlanNotDefaultException.class, ()->planUseCase.makePlanFromDefault(plan.id));
    }

    @Test
    public void testStartPlanForRemainingItems(){
        Plan plan = planUseCase.startPlan("", "");
        planUseCase.addItem(plan.id, new PlanItem("I1", "D1"));
        planUseCase.addItem(plan.id, new PlanItem("I2", "D2"));
        final String unchecked_item = "Unchecked Item";
        planUseCase.addItem(plan.id, new PlanItem(unchecked_item, "should be copied"));
        planUseCase.getItems(plan.id).stream()
                .filter(item->!item.title.equals(unchecked_item))
                .forEach(item->planUseCase.checkItem(item.id));
        Plan remainingItemsPlan = planUseCase.startPlanForRemainingItems(plan.id, "RemainTitle", "Remain Description");
        plan = planUseCase.getPlanById(plan.id);
        assertTrue(plan.done);
        assertFalse(remainingItemsPlan.done);
        assertEquals(remainingItemsPlan.title, "RemainTitle");
        assertEquals(remainingItemsPlan.description, "Remain Description");

        final List<PlanItem> remainingItems = planUseCase.getItems(remainingItemsPlan.id);
        assertEquals(1, remainingItems.size());
        final PlanItem uncheckedItem = remainingItems.iterator().next();
        assertFalse(uncheckedItem.checked);
        assertEquals(unchecked_item, uncheckedItem.title);
    }

    @Test
    public void testStartPlanForRemainingItemsPlanNotFoundException(){
        assertThrows(PlanNotFoundException.class, ()->planUseCase.startPlanForRemainingItems(1L, "", ""));
    }

    @Test
    public void testSetTimeInterval(){
        final Plan plan = planUseCase.startPlan("", "");
        planUseCase.addItem(plan.id, new PlanItem("Brush teeth", "straight movements"));
        final PlanItem item = planUseCase.getItems(plan.id).iterator().next();
        assertNull(item.startDate);
        assertNull(item.timeInterval);
        final LocalDate testDate = LocalDate.now();
        final Period interval = Period.ofDays(14);
        planUseCase.setTimeInterval(item.id, testDate, interval);
        PlanItem itemWithInterval = planUseCase.getItems(plan.id).iterator().next();
        assertEquals(testDate, itemWithInterval.startDate);
        assertEquals(interval, itemWithInterval.timeInterval);
    }

    @Test
    public void testSetTimeIntervalPlanItemNotFoundException(){
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.setTimeInterval(1L, null, Period.ofDays(1)));
    }
}
