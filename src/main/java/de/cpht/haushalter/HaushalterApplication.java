package de.cpht.haushalter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HaushalterApplication {//implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HaushalterApplication.class, args);
	}

//	@Autowired
//	private PlanRepository planRepository;
//	@Autowired
//	private PlanItemRepository itemRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//		JpaPlan plan = new JpaPlan();
//		plan.setTitle("Küche");
//		plan.setDescription("Erst die Oberflächen!");
//		plan.setType(PlanType.TIMEDLIST);
//		planRepository.save(plan);
//
//		JpaPlanItem item = new JpaPlanItem();
//		item.setTitle("Spülmaschine ausräumen");
//		item.setDescription("Becher unten trocknen");
//		item.setPlan(plan);
//		itemRepository.save(item);
//	}
}
