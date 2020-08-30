package de.cpht.haushalter.adapters.repository;

import de.cpht.haushalter.domain.entities.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItem, Long> {
    List<PlanItem> findByPlan(Plan plan);
}
