package de.cpht.haushalter.adapters.db.jpa.entity;

import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanType;

import javax.persistence.*;

@Entity
@Table(name = "plan")
public class JpaPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    private boolean done;
    private PlanType type;

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public PlanType getType() {
        return type;
    }

    public void setType(PlanType type) {
        this.type = type;
    }

    public void update(Plan updatedPlan) {
        this.title = updatedPlan.title;
        this.description = updatedPlan.description;
        this.done = updatedPlan.done;
    }

}
