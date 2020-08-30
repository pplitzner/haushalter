package de.cpht.haushalter.adapters.repository;

import de.cpht.haushalter.domain.entities.PlanDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

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

    public void update(PlanDTO updatedPlan) {
        this.title = updatedPlan.title;
        this.description = updatedPlan.description;
    }

    public PlanDTO dto(){
        PlanDTO dto = new PlanDTO();
        dto.id = this.id;
        dto.title = this.title;
        dto.description = this.description;
        return dto;
    }
}
