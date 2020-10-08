package de.cpht.haushalter.domain.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

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
}
