package de.cpht.haushalter.domain.entities;

public class Plan {
    public Long id;
    public String title;
    public String description;
    public boolean done;
    public boolean isDefault;
    public PlanType type;

    public Plan(){}

    public Plan(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
