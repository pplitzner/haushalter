package de.cpht.haushalter.adapters.db.jpa.repository;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.domain.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Period;
import java.util.List;

public interface PlanItemRepository extends JpaRepository<JpaPlanItem, Long> {
    List<JpaPlanItem> findByPlan(JpaPlan plan);
    List<JpaPlanItem> findByType(ItemType type);
    List<JpaPlanItem> findByTimeInterval(Period timeInterval);
    List<JpaPlanItem> findByTimeIntervalNot(Period timeInterval);
}
