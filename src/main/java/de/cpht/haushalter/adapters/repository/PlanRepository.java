package de.cpht.haushalter.adapters.repository;

import de.cpht.haushalter.domain.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
