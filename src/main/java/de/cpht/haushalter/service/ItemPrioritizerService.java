package de.cpht.haushalter.service;

import de.cpht.haushalter.domain.usecases.ItemPrioritizer;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ItemPrioritizerService {
    @Autowired
    private PlanUseCase planUseCase;

    @Scheduled(fixedRate = 3600000) // 1h
    private void setItemPriority() {
        ItemPrioritizer.setPriority(planUseCase);
    }
}
