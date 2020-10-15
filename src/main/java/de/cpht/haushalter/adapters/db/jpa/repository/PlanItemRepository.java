package de.cpht.haushalter.adapters.db.jpa.repository;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<JpaPlanItem, Long> {
    List<JpaPlanItem> findByPlan(JpaPlan plan);
    List<JpaPlanItem> findByIsChecked(boolean isChecked);
}
