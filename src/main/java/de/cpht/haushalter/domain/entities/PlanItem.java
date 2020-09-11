package de.cpht.haushalter.domain.entities;

public class PlanItem {
    public Long id;
    public String title;
    public String description;

    public boolean checked;

    public Long planId;

    public PlanItem(){}

    public PlanItem(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
