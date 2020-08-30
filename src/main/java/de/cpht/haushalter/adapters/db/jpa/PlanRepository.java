package de.cpht.haushalter.adapters.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanJpaEntity, Long> {
}
