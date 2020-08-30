package de.cpht.haushalter.adapters.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanItemRepository extends JpaRepository<PlanItemJpaEntity, Long> {
    List<PlanItemJpaEntity> findByPlanId(Long id);
}
