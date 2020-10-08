package de.cpht.haushalter.domain.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class PlanItem {
    public Long id;
    public String title;
    public String description;

    public boolean checked;

    public Long planId;

    public LocalDate startDate;
    public Period timeInterval;
    public Duration duration;

    public PlanItem(){}

    public PlanItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public PlanItem(String title, String description, LocalDate startDate, Period timeInterval, Duration duration) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.timeInterval = timeInterval;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanItem planItem = (PlanItem) o;
        return checked == planItem.checked &&
                title.equals(planItem.title) &&
                Objects.equals(description, planItem.description) &&
                Objects.equals(startDate, planItem.startDate) &&
                Objects.equals(timeInterval, planItem.timeInterval) &&
                Objects.equals(duration, planItem.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, checked, startDate, timeInterval, duration);
    }
}
