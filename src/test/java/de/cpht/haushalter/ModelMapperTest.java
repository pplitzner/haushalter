package de.cpht.haushalter;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.domain.entities.*;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModelMapperTest {

    @Test
    public void testPlanToPlanDTOMapping() {
        ModelMapper mapper = new ModelMapper();
        JpaPlan plan = new JpaPlan();
        plan.setTitle("p1");
        plan.setDescription("d1");
        plan.setType(PlanType.TIMEDLIST);
        plan.setDone(true);

        final Plan planDTO = mapper.map(plan, Plan.class);

        assertEquals(plan.getTitle(), planDTO.getTitle());
        assertEquals(plan.getDescription(), planDTO.getDescription());
        assertEquals(plan.getId(), planDTO.getId());
        assertEquals(plan.getType(), planDTO.getType());
    }

    @Test
    public void testPlanItemDTOToPlanItemMapping() {
        ModelMapper mapper = new ModelMapper();
        PlanItem item = new PlanItem();
        item.title = "i1";
        item.description = "id1";
        item.type = ItemType.TIMED;
        item.timeInterval = Period.ofDays(2);
        item.duration = Duration.ofMinutes(3);
        item.checkedAt = LocalDateTime.now();
        item.priority = ItemPriority.RED_ALERT;

        final JpaPlanItem planItem = mapper.map(item, JpaPlanItem.class);

        assertEquals(item.getId(), planItem.getId());
        assertEquals(item.getTitle(), planItem.getTitle());
        assertEquals(item.getDescription(), planItem.getDescription());
        assertEquals(item.getType(), planItem.getType());
        assertEquals(item.getTimeInterval(), planItem.getTimeInterval());
        assertEquals(item.getDuration(), planItem.getDuration());
        assertEquals(item.getCheckedAt(), planItem.getCheckedAt());
        assertEquals(item.getPriority(), planItem.getPriority());
    }

    @Test
    public void testPlanItemToPlanItemDTOMapping() {
        ModelMapper mapper = new ModelMapper();

        JpaPlan plan = new JpaPlan();
        plan.setTitle("p1");
        plan.setDescription("d1");
        plan.setType(PlanType.TIMEDLIST);
        plan.setDone(true);

        JpaPlanItem item = new JpaPlanItem();
        item.setTitle("i1");
        item.setDescription("id1");
        item.setType(ItemType.TIMED);
        item.setTimeInterval(Period.ofDays(2));
        item.setDuration(Duration.ofMinutes(3));
        item.setCheckedAt(LocalDateTime.now());
        item.setPriority(ItemPriority.RED_ALERT);

        item.setPlan(plan);

        final PlanItem itemDTO = mapper.map(item, PlanItem.class);

        assertEquals(item.getId(), itemDTO.getId());
        assertEquals(item.getTitle(), itemDTO.getTitle());
        assertEquals(item.getDescription(), itemDTO.getDescription());
        assertEquals(item.getType(), itemDTO.getType());
        assertEquals(item.getTimeInterval(), itemDTO.getTimeInterval());
        assertEquals(item.getDuration(), itemDTO.getDuration());
        assertEquals(item.getCheckedAt(), itemDTO.getCheckedAt());
        assertEquals(item.getPriority(), itemDTO.getPriority());

        assertEquals(item.getPlan().getId(), itemDTO.getPlanId());
        assertEquals(item.getPlan().getTitle(), itemDTO.getPlanTitle());

    }
}
