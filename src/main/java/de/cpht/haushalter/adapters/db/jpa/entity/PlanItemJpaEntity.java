package de.cpht.haushalter.adapters.db.jpa.entity;

import de.cpht.haushalter.domain.entities.PlanItem;

import javax.persistence.*;

@Entity
@Table(name = "plan_item")
public class PlanItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
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

    public PlanItem dto(){
        PlanItem item = new PlanItem();
        item.id = this.id;
        item.title = this.title;
        item.description = this.description;
        item.planId = this.plan.getId();
        return item;
    }

    public void update(PlanItem item) {
        this.title = item.title;
        this.description = item.description;
    }
}
