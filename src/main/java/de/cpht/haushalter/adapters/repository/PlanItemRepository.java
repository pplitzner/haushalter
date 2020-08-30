package de.cpht.haushalter.adapters.repository;

import de.cpht.haushalter.domain.entities.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItemJpaEntity, Long> {
    List<PlanItemJpaEntity> findByPlan(PlanJpaEntity plan);
}
