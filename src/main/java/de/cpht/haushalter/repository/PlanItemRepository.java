package de.cpht.haushalter.repository;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.model.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItem, Long> {
    List<PlanItem> findByPlan(Plan plan);
}
