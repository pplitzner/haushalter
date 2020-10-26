package de.cpht.haushalter.adapters.db.jpa.repository;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.domain.entities.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<JpaPlan, Long> {
    List<JpaPlan> findByIsDefault(boolean isDefault);
    List<JpaPlan> findByTypeOrderByTitle(PlanType type);
}
