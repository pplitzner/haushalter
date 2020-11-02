package de.cpht.haushalter.domain.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

public class PlanItem {
    public Long id;
    public String title;
    public String description;

    public LocalDateTime checkedAt;

    public Long planId;
    public String planTitle; //cache field

    public Period timeInterval;
    public Duration duration;

    public ItemType type;
    public ItemPriority priority;

    public PlanItem(){}

    public PlanItem(String title, String description) {
        this.title = title;
        this.description = description;
        type = ItemType.DEFAULT;
    }

    public PlanItem(String title, String description, Period timeInterval, Duration duration, ItemType type) {
        this.title = title;
        this.description = description;
        this.timeInterval = timeInterval;
        this.duration = duration;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanItem planItem = (PlanItem) o;
        return title.equals(planItem.title) &&
                Objects.equals(description, planItem.description) &&
                Objects.equals(timeInterval, planItem.timeInterval) &&
                Objects.equals(duration, planItem.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, timeInterval, duration);
    }
}
