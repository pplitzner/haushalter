package de.cpht.haushalter.adapters.db.jpa.repository;

import de.cpht.haushalter.adapters.db.jpa.entity.PlanItemJpaEntity;
import de.cpht.haushalter.adapters.db.jpa.entity.PlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItemJpaEntity, Long> {
    List<PlanItemJpaEntity> findByPlan(PlanJpaEntity plan);
}
