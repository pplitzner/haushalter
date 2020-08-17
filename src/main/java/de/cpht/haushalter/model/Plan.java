package de.cpht.haushalter.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PlanItem> items;

    public Plan(String title, String description) {
        this.title = title;
        this.description = description;
    }

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

    public List<PlanItem> getItems() {
        return items;
    }

    public void setItems(List<PlanItem> items) {
        this.items = items;
    }

}
