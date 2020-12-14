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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public Period getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Period timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemPriority getPriority() {
        return priority;
    }

    public void setPriority(ItemPriority priority) {
        this.priority = priority;
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
