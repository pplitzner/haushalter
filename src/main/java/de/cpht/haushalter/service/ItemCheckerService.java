package de.cpht.haushalter.service;

import de.cpht.haushalter.domain.usecases.ItemChecker;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ItemCheckerService {

    @Autowired
    private PlanUseCase planUseCase;

    @Scheduled(fixedRate = 60000) // 60s
    private void checkItems(){
        ItemChecker.checkItems(planUseCase);
    }

}
