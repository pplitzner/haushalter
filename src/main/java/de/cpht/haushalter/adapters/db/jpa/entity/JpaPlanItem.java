package de.cpht.haushalter.adapters.db.jpa.entity;

import de.cpht.haushalter.domain.entities.PlanItem;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "plan_item")
public class JpaPlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    private boolean checked;

    private LocalDate startDate;
    private Period timeInterval; // variable can not be called "interval" -> SQL exception

    @ManyToOne
    private JpaPlan plan;

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

    public JpaPlan getPlan() {
        return plan;
    }

    public void setPlan(JpaPlan plan) {
        this.plan = plan;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Period getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Period timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void update(PlanItem item) {
        this.title = item.title;
        this.description = item.description;
        this.startDate = item.startDate;
        this.timeInterval = item.timeInterval;
    }
}
