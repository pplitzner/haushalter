package de.cpht.haushalter.repository;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.model.PlanItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class PlanItemRepositoryTest {

    @Autowired
    private PlanItemRepository planItemRepository;
    @Autowired
    private PlanRepository planRepository;

    @Test
    public void addPlanItem(){
        Plan plan = new Plan("Küche", "Oben und unten");
        planRepository.save(plan);
        assertFalse(planRepository.findAll().isEmpty());

        PlanItem item = new PlanItem(
                "Spülmaschine",
                "Tassen mit dem Handtuch trocknen!",
                plan);
        planItemRepository.save(item);
        List<PlanItem> items = planItemRepository.findByPlan(plan);
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        assertEquals(item, items.iterator().next());
    }
}
