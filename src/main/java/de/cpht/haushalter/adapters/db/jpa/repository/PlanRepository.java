package de.cpht.haushalter.adapters.db.jpa.repository;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<JpaPlan, Long> {
}
