package de.cpht.haushalter.adapters.db.jpa.entity;

import de.cpht.haushalter.domain.entities.PlanItem;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "plan_item")
public class JpaPlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    private boolean isChecked;
    private LocalDateTime checkedAt;

    private LocalDateTime startDate;
    private Period timeInterval; // variable can not be called "interval" -> SQL exception
    private Duration duration;

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
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Period getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Period timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void update(PlanItem item) {
        this.title = item.title;
        this.description = item.description;
        this.startDate = item.startDate;
        this.timeInterval = item.timeInterval;
        this.duration = item.duration;
    }
}
