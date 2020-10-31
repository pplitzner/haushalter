package de.cpht.haushalter.adapters.db.jpa;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanItem;

public class DtoMapper {

    public static JpaPlanItem jpaItemFrom(PlanItem item){
        final JpaPlanItem jpaPlanItem = new JpaPlanItem();
        jpaPlanItem.setTitle(item.title);
        jpaPlanItem.setDescription(item.description);
        jpaPlanItem.setCheckedAt(item.checkedAt);
        jpaPlanItem.setTimeInterval(item.timeInterval);
        jpaPlanItem.setDuration(item.duration);
        return jpaPlanItem;
    }

    public static PlanItem dtoFrom(JpaPlanItem jpaPlanItem){
        PlanItem item = new PlanItem();
        item.id = jpaPlanItem.getId();
        item.title = jpaPlanItem.getTitle();
        item.description = jpaPlanItem.getDescription();
        item.planId = jpaPlanItem.getPlan().getId();
        item.checkedAt = jpaPlanItem.getCheckedAt();
        item.timeInterval = jpaPlanItem.getTimeInterval();
        item.duration = jpaPlanItem.getDuration();
        return item;
    }


    public static Plan dtoFrom(JpaPlan jpaPlan){
        Plan dto = new Plan();
        dto.id = jpaPlan.getId();
        dto.title = jpaPlan.getTitle();
        dto.description = jpaPlan.getDescription();
        dto.done = jpaPlan.isDone();
        dto.type = jpaPlan.getType();
        return dto;
    }
}
