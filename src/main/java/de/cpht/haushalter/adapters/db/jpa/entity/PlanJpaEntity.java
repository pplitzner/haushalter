package de.cpht.haushalter.adapters.db.jpa.entity;

import de.cpht.haushalter.domain.entities.Plan;

import javax.persistence.*;

@Entity
@Table(name = "plan")
public class PlanJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    private boolean done;

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

    public void update(Plan updatedPlan) {
        this.title = updatedPlan.title;
        this.description = updatedPlan.description;
    }

    public Plan dto(){
        Plan dto = new Plan();
        dto.id = this.id;
        dto.title = this.title;
        dto.description = this.description;
        dto.done = this.done;
        return dto;
    }
}
