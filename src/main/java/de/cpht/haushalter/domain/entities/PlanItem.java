package de.cpht.haushalter.domain.entities;

import java.time.LocalDate;
import java.util.Objects;

public class PlanItem {
    public Long id;
    public String title;
    public String description;

    public boolean checked;

    public Long planId;

    public Long timeInterval;
    public LocalDate startDate;

    public PlanItem(){}

    public PlanItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanItem planItem = (PlanItem) o;
        return checked == planItem.checked &&
                Objects.equals(title, planItem.title) &&
                Objects.equals(description, planItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, checked);
    }
}
