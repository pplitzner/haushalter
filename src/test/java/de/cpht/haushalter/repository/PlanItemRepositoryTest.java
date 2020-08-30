package de.cpht.haushalter.repository;

import de.cpht.haushalter.adapters.repository.PlanItemRepository;
import de.cpht.haushalter.adapters.repository.PlanRepository;
import de.cpht.haushalter.adapters.repository.PlanJpaEntity;
import de.cpht.haushalter.domain.entities.PlanItem;
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
        PlanJpaEntity plan = new PlanJpaEntity();
        plan.setTitle("Küche");
        plan.setDescription("Oben und unten");
        planRepository.save(plan);
        assertFalse(planRepository.findAll().isEmpty());

        PlanItem item = new PlanItem();
        item.setTitle("Spülmaschine");
        item.setDescription("Tassen mit dem Handtuch trocknen!");
        item.setPlan(plan);
        planItemRepository.save(item);
        List<PlanItem> items = planItemRepository.findByPlan(plan);
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        PlanItem next = items.iterator().next();
        assertEquals("Spülmaschine", next.getTitle());
        assertEquals("Tassen mit dem Handtuch trocknen!", next.getDescription());
        assertEquals(plan, next.getPlan());
    }
}
