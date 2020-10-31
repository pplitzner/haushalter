package de.cpht.haushalter;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanItemRepository;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanRepository;
import de.cpht.haushalter.domain.entities.PlanType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class HaushalterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HaushalterApplication.class, args);
	}

//	@Autowired
//	private PlanRepository planRepository;
//	@Autowired
//	private PlanItemRepository itemRepository;

	@Override
	public void run(String... args) throws Exception {
//		JpaPlan plan = new JpaPlan();
//		plan.setTitle("Küche");
//		plan.setDescription("Erst die Oberflächen!");
//		plan.setType(PlanType.TIMEDLIST);
//		planRepository.save(plan);
//
//		JpaPlanItem item = new JpaPlanItem();
//		item.setTitle("Spülmaschine ausräumen");
//		item.setDescription("Becher unten trocknen");
//		item.setDuration(Duration.ofMinutes(15));
//		item.setCheckedAt(LocalDateTime.now().minusDays(1));
//		item.setPlan(plan);
//		itemRepository.save(item);
	}
}
