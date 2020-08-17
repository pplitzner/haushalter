package de.cpht.haushalter.repository;

import de.cpht.haushalter.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
