package de.cpht.haushalter.domain.entities;

import de.cpht.haushalter.adapters.repository.PlanJpaEntity;

import javax.persistence.*;

public class PlanItem {
    private Long id;
    private String title;
    private String description;

    private PlanJpaEntity plan;

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

    public PlanJpaEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanJpaEntity plan) {
        this.plan = plan;
    }
}
