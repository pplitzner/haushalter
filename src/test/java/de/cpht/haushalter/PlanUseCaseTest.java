package de.cpht.haushalter;

import de.cpht.haushalter.domain.entities.*;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import de.cpht.haushalter.exception.*;
import de.cpht.haushalter.service.ItemPrioritizerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PlanUseCaseTest {

    @MockBean
    private ItemPrioritizerService itemPrioritizerService; // mock away for testing

    @Autowired
    private PlanUseCase planUseCase;

    @Test
    public void testStartPlanShowAllPlans(){
        assertTrue(planUseCase.showAllPlans(PlanType.DEFAULT).isEmpty());
        assertTrue(planUseCase.showAllPlans(PlanType.CHECKLIST).isEmpty());
        assertTrue(planUseCase.showAllPlans(PlanType.TIMEDLIST).isEmpty());

        Plan plan = planUseCase.startPlan("Test Title", "Test description", PlanType.DEFAULT);

        assertTrue(planUseCase.showAllPlans(PlanType.CHECKLIST).isEmpty());
        assertTrue(planUseCase.showAllPlans(PlanType.TIMEDLIST).isEmpty());
        List<Plan> plans = planUseCase.showAllPlans(PlanType.DEFAULT);
        assertEquals(1, plans.size());
        assertFalse(plan.done);
        assertEquals(PlanType.DEFAULT, plan.type);
    }

    @Test
    public void testDeletePlan(){
        Plan plan = planUseCase.startPlan("Test Title", "Test description", PlanType.CHECKLIST);
        assertEquals(1, planUseCase.showAllPlans(PlanType.CHECKLIST).size());
        planUseCase.deletePlan(plan.id);
        assertTrue(planUseCase.showAllPlans(PlanType.CHECKLIST).isEmpty());
    }

    @Test
    public void testDeletePlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.deletePlan(1L));
    }

    @Test
    public void testGetPlanById(){
        Plan plan = planUseCase.startPlan("Test Title", "Test description", PlanType.CHECKLIST);
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
        Plan plan = planUseCase.startPlan("Test Title1", "Test description1", PlanType.CHECKLIST);
        Plan updatedPlan = new Plan();
        updatedPlan.title = "Test Title2";
        updatedPlan.description = "Test description2";
        updatedPlan.done = true;

        planUseCase.updatePlan(plan.id, updatedPlan);
        Plan planById = planUseCase.getPlanById(plan.id);
        assertNotNull(planById);
        assertEquals(plan.id, planById.id);
        assertEquals(updatedPlan.title, planById.title);
        assertEquals(updatedPlan.description, planById.description);
        assertEquals(updatedPlan.done, planById.done);
    }

    @Test
    public void testUpdatePlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.updatePlan(1L, new Plan()));
    }

    @Test
    public void testUpdatePlanFinishedException() {
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
        planUseCase.finishPlan(plan.id, false);
        assertThrows(PlanFinishedException.class, ()->planUseCase.updatePlan(plan.id, new Plan()));
    }

    @Test
    public void testFinishPlan(){
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
        Plan planById = planUseCase.getPlanById(plan.id);
        assertFalse(planById.done);
        planUseCase.finishPlan(plan.id, false);
        planById = planUseCase.getPlanById(plan.id);
        assertTrue(planById.done);
    }

    @Test
    public void testFinishPlanWithStartPlanForRemainingItems(){
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
        PlanItem unfinishedItem = new PlanItem("","");
        PlanItem finishedItem = new PlanItem("","");
        finishedItem.checkedAt = LocalDateTime.now();
        planUseCase.addItem(plan.id, unfinishedItem);
        planUseCase.addItem(plan.id, finishedItem);

        Plan planById = planUseCase.getPlanById(plan.id);
        assertFalse(planById.done);
        final Plan planForRemainingItems = planUseCase.finishPlan(plan.id, true);
        assertNotNull(planForRemainingItems);

        planById = planUseCase.getPlanById(plan.id);
        assertTrue(planById.done);

        assertFalse(planForRemainingItems.done);
        final List<PlanItem> remainingItems = planUseCase.getItems(planForRemainingItems.id);
        assertFalse(remainingItems.isEmpty());
        assertTrue(remainingItems.contains(unfinishedItem));
    }

    @Test
    public void testFinishPlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.finishPlan(1L, false));
    }

    @Test
    public void testFinishPlanFinishedException() {
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
        assertDoesNotThrow(()->planUseCase.finishPlan(plan.id, false));
        assertThrows(PlanFinishedException.class, ()->planUseCase.finishPlan(plan.id, false));
    }

    @Test
    public void testGetItemsByTimeInterval(){
        final Plan plan = planUseCase.startPlan("", "", PlanType.TIMEDLIST);
        final PlanItem i1 = planUseCase.addItem(plan.id, new PlanItem("i1", ""));
        i1.timeInterval = Period.ofDays(1);
        planUseCase.updateItem(i1.id, i1);
        final PlanItem i2 = planUseCase.addItem(plan.id, new PlanItem("i2", ""));
        i2.timeInterval = Period.ofMonths(1);
        planUseCase.updateItem(i2.id, i2);

        final List<PlanItem> dailys = planUseCase.getItemsByTimeInterval(Period.ofDays(1));
        assertEquals(1, dailys.size());
        assertTrue(dailys.contains(i1));

        final List<PlanItem> monthlys = planUseCase.getItemsByTimeInterval(Period.ofMonths(1));
        assertEquals(1, monthlys.size());
        assertTrue(monthlys.contains(i2));
    }

    @Test
    public void testGetItemsByType(){
        final Plan timedPlan = planUseCase.startPlan("", "", PlanType.TIMEDLIST);
        final PlanItem timedItem = planUseCase.addItem(timedPlan.id, new PlanItem("timed", "item"));

        final Plan checklist = planUseCase.startPlan("", "", PlanType.CHECKLIST);
        final PlanItem defaultItem = planUseCase.addItem(checklist.id, new PlanItem("default", "item"));

        final List<PlanItem> timedItems = planUseCase.getItemsByType(ItemType.TIMED);
        final List<PlanItem> defaultItems = planUseCase.getItemsByType(ItemType.DEFAULT);

        assertEquals(1, timedItems.size());
        assertTrue(timedItems.contains(timedItem));
        assertEquals(1, defaultItems.size());
        assertTrue(defaultItems.contains(defaultItem));

    }

    @Test
    public void testCheckedItems(){
        final Plan plan1 = planUseCase.startPlan("Plan1", "", PlanType.CHECKLIST);
        final PlanItem item1 = planUseCase.addItem(plan1.id, new PlanItem("", ""));

        final PlanItem item2 = planUseCase.addItem(plan1.id, new PlanItem("", ""));

        final Plan plan2 = planUseCase.startPlan("", "", PlanType.CHECKLIST);
        final PlanItem item3 = planUseCase.addItem(plan2.id, new PlanItem("", ""));

        planUseCase.checkItem(item2.id);
        planUseCase.checkItem(item3.id);
        List<PlanItem> defaultItems = planUseCase.getItemsByType(ItemType.DEFAULT);
        final List<PlanItem> checkedItems = defaultItems.stream()
                .filter(planItem -> planItem.checkedAt != null).collect(Collectors.toList());
        final List<PlanItem> uncheckedItems = defaultItems.stream()
                .filter(planItem -> planItem.checkedAt == null).collect(Collectors.toList());
        assertEquals(2, checkedItems.size());
        assertEquals(1, uncheckedItems.size());

        assertTrue(checkedItems.stream().anyMatch(item->item.id==item3.id));
        assertTrue(checkedItems.stream().anyMatch(item->item.id==item2.id));
        assertTrue(checkedItems.stream().noneMatch(item->item.id==item1.id));

        assertTrue(uncheckedItems.stream().noneMatch(item->item.id==item2.id));
        assertTrue(uncheckedItems.stream().noneMatch(item->item.id==item3.id));
        assertTrue(uncheckedItems.stream().anyMatch(item->item.id==item1.id));
        assertTrue(uncheckedItems.stream().anyMatch(item->item.planTitle==plan1.title));
    }

    @Test
    public void testGetItemsAddItem(){
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
        List<PlanItem> items = planUseCase.getItems(plan.id);
        assertTrue(items.isEmpty());

        PlanItem item = new PlanItem("it", "id");
        planUseCase.addItem(plan.id, item);
        items = planUseCase.getItems(plan.id);
        assertFalse(items.isEmpty());
        PlanItem next = items.iterator().next();
        assertEquals(item.title, next.title);
        assertEquals(item.description, next.description);
        assertEquals(plan.title, next.planTitle);
    }

    @Test
    public void testAddTimedItemToTimedList(){
        final Plan timedPlan = planUseCase.startPlan("timed plans", "", PlanType.TIMEDLIST);
        final PlanItem timedItem = planUseCase.addItem(timedPlan.id, new PlanItem("timed item", "td"));
        assertEquals(ItemType.TIMED, timedItem.type);
    }

    @Test
    public void testAddItemPlanNotFoundException() {
        assertThrows(PlanNotFoundException.class, ()->planUseCase.getItems(1L));
        assertThrows(PlanNotFoundException.class, ()->planUseCase.addItem(1L, new PlanItem()));
    }

    @Test
    public void testDeleteItem(){
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
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
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
        Long itemId = planUseCase.addItem(plan.id, new PlanItem("it", "id")).id;
        PlanItem item = new PlanItem("item title2", "item description2");
        item.timeInterval = Period.ofDays(14);
        item.duration = Duration.ofMinutes(10);

        planUseCase.updateItem(itemId, item);

        PlanItem updatedItem = planUseCase.getItems(plan.id).iterator().next();
        assertEquals(itemId, updatedItem.id);
        assertEquals(item.title, updatedItem.title);
        assertEquals(item.description, updatedItem.description);
        assertEquals(item.timeInterval, updatedItem.timeInterval);
        assertEquals(item.duration, updatedItem.duration);
    }

    @Test
    public void testUpdatePlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.updateItem(1L, new PlanItem()));
    }

    @Test
    public void testCheckItem(){
        Plan plan = planUseCase.startPlan("t", "d", PlanType.CHECKLIST);
        PlanItem item = planUseCase.addItem(plan.id, new PlanItem("it", "id"));
        assertNull(item.checkedAt);

        LocalDateTime now = LocalDateTime.now();
        planUseCase.checkItem(item.id);
        item = planUseCase.getItems(plan.id).iterator().next();
        assertNotNull(item.checkedAt);
        assertTrue(now.isBefore(item.checkedAt));

        planUseCase.checkItem(item.id);
        item = planUseCase.getItems(plan.id).iterator().next();
        assertNull(item.checkedAt);
    }

    @Test
    public void testCheckTimedItem(){
        Plan plan = planUseCase.startPlan("t", "d", PlanType.TIMEDLIST);
        PlanItem item = planUseCase.addItem(plan.id, new PlanItem("it", "id"));
        item.timeInterval = Period.ofMonths(1);
        item = planUseCase.updateItem(item.id, item);
        assertNull(item.checkedAt);

        LocalDateTime now = LocalDateTime.now();
        planUseCase.checkItem(item.id);
        item = planUseCase.getItems(plan.id).iterator().next();
        assertNotNull(item.checkedAt);
        assertTrue(now.isBefore(item.checkedAt));

        assertNotNull(item.priority);
        assertEquals(ItemPriority.NOT_AVAILABLE, item.priority);

    }

    @Test
    public void testCheckTimedItemNotFinishableException(){
        Plan plan = planUseCase.startPlan("t", "d", PlanType.TIMEDLIST);
        PlanItem item = planUseCase.addItem(plan.id, new PlanItem("it", "id"));
        item.timeInterval = Period.ofMonths(1);
        PlanItem updatedItem = planUseCase.updateItem(item.id, item);
        planUseCase.checkItem(updatedItem.id);
        assertThrows(TimedItemNotFinishableException.class, ()->planUseCase.checkItem(updatedItem.id));

    }

    @Test
    public void testCheckItemPlanItemNotFoundException() {
        assertThrows(PlanItemNotFoundException.class, ()->planUseCase.checkItem(1L));
    }

    @Test
    public void testFinishTimedItem() {
        final Plan plan = planUseCase.startPlan("", "", PlanType.TIMEDLIST);
        PlanItem planItem = planUseCase.addItem(plan.id, new PlanItem("", ""));
        assertNull(planItem.checkedAt);
        planUseCase.checkItem(planItem.id);
        planItem = planUseCase.getItems(plan.id).iterator().next();
        assertNotNull(planItem.checkedAt);
        final LocalDateTime checkedAtFirstTime = planItem.checkedAt;
        planUseCase.checkItem(planItem.id);
        planItem = planUseCase.getItems(plan.id).iterator().next();
        assertNotNull(planItem.checkedAt);
        assertTrue(checkedAtFirstTime.isBefore(planItem.checkedAt));
    }

    @Test
    public void testStartPlanFromDefault(){
        Plan defaultPlan = planUseCase.startPlan("Default", "plan", PlanType.DEFAULT);
        planUseCase.addItem(defaultPlan.id, new PlanItem("Item1", "Description1"));
        planUseCase.addItem(defaultPlan.id, new PlanItem("Item2", "Description2"));
        Plan plan = planUseCase.startPlanFromDefault(defaultPlan.id);
        assertEquals(defaultPlan.title, plan.title);
        assertEquals(defaultPlan.description, plan.description);
        assertNotEquals(defaultPlan.id, plan.id);
        assertNotEquals(PlanType.DEFAULT, plan.type);

        assertEquals(planUseCase.getItems(defaultPlan.id).iterator().next(), planUseCase.getItems(plan.id).iterator().next());
    }

    @Test
    public void teststartPlanFromDefaultPlanNotDefaultException(){
        Plan plan = planUseCase.startPlan("", "", PlanType.CHECKLIST);
        assertThrows(PlanNotDefaultException.class, ()->planUseCase.startPlanFromDefault(plan.id));

    }

    @Test
    public void testCheckPlanDone(){
        Plan plan = planUseCase.startPlan("", "", PlanType.CHECKLIST);
        final PlanItem itemDone = new PlanItem("", "");
        itemDone.checkedAt = LocalDateTime.now();
        planUseCase.addItem(plan.id, itemDone);
        assertTrue(planUseCase.checkPlanDone(plan.id));

        final PlanItem itemmNotDone = new PlanItem("", "");
        planUseCase.addItem(plan.id, itemmNotDone);

        assertFalse(planUseCase.checkPlanDone(plan.id));
    }

    @Test
    public void testStartPlanForRemainingItems(){
        Plan plan = planUseCase.startPlan("", "", PlanType.CHECKLIST);
        planUseCase.addItem(plan.id, new PlanItem("I1", "D1"));
        planUseCase.addItem(plan.id, new PlanItem("I2", "D2"));
        final String unchecked_item = "Unchecked Item";
        planUseCase.addItem(plan.id, new PlanItem(unchecked_item, "should be copied"));
        planUseCase.getItems(plan.id).stream()
                .filter(item->!item.title.equals(unchecked_item))
                .forEach(item->planUseCase.checkItem(item.id));
        Plan remainingItemsPlan = planUseCase.startPlanForRemainingItems(plan.id);
        plan = planUseCase.getPlanById(plan.id);
        assertTrue(plan.done);
        assertFalse(remainingItemsPlan.done);
        assertEquals(remainingItemsPlan.title, plan.title);
        assertEquals(remainingItemsPlan.description, plan.description);

        final List<PlanItem> remainingItems = planUseCase.getItems(remainingItemsPlan.id);
        assertEquals(1, remainingItems.size());
        final PlanItem uncheckedItem = remainingItems.iterator().next();
        assertNull(uncheckedItem.checkedAt);
        assertEquals(unchecked_item, uncheckedItem.title);
    }

    @Test
    public void testStartPlanForRemainingItemsPlanNotFoundException(){
        assertThrows(PlanNotFoundException.class, ()->planUseCase.startPlanForRemainingItems(1L));
    }
}
