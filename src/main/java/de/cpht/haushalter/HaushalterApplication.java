package de.cpht.haushalter;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.model.PlanItem;
import de.cpht.haushalter.repository.PlanItemRepository;
import de.cpht.haushalter.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HaushalterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HaushalterApplication.class, args);
	}

	@Autowired
	private PlanRepository planRepository;
	@Autowired
	private PlanItemRepository itemRepository;

	@Override
	public void run(String... args) throws Exception {
		Plan plan = new Plan();
		plan.setTitle("Küche");
		plan.setDescription("Erst die Oberflächen!");
		planRepository.save(plan);

		PlanItem item = new PlanItem();
		item.setTitle("Spülmaschine ausräumen");
		item.setDescription("Becher unten trocknen");
		item.setPlan(plan);
		itemRepository.save(item);
	}
}
