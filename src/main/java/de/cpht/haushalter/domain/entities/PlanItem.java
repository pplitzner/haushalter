package de.cpht.haushalter.domain.entities;

import de.cpht.haushalter.adapters.db.jpa.PlanJpaEntity;

public class PlanItem {
    public Long id;
    public String title;
    public String description;

    public PlanJpaEntity plan;
}
