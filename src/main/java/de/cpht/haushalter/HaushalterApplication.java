package de.cpht.haushalter;

import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlan;
import de.cpht.haushalter.adapters.db.jpa.entity.JpaPlanItem;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanItemRepository;
import de.cpht.haushalter.adapters.db.jpa.repository.PlanRepository;
import de.cpht.haushalter.domain.entities.ItemType;
import de.cpht.haushalter.domain.entities.PlanType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@SpringBootApplication
@EnableScheduling
public class HaushalterApplication {// implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HaushalterApplication.class, args);
	}

//	@Autowired
//	private PlanRepository planRepository;
//	@Autowired
//	private PlanItemRepository itemRepository;

	@Bean
	public ModelMapper modelMapper() {
		final ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
//
//	@Override
//	public void run(String... args) {
//		JpaPlan plan = new JpaPlan();
//		plan.setTitle("Küche");
//		plan.setDescription("Erst die Oberflächen!");
//		plan.setType(PlanType.TIMEDLIST);
//		planRepository.save(plan);
//
//		JpaPlanItem item = new JpaPlanItem();
//		item.setTitle("Spülmaschine ausräumen");
//		item.setDescription("Becher unten trocknen");
//		item.setType(ItemType.TIMED);
//		item.setDuration(Duration.ofMinutes(15));
//		item.setTimeInterval(Period.ofMonths(1));
//		item.setCheckedAt(LocalDateTime.now().minusWeeks(3));
//		item.setPlan(plan);
//		itemRepository.save(item);
//
//		JpaPlanItem item2 = new JpaPlanItem();
//		item2.setTitle("Teppiche saugen");
//		item2.setType(ItemType.TIMED);
//		item2.setDuration(Duration.ofMinutes(15));
//		item2.setTimeInterval(Period.ofMonths(1));
//		item2.setCheckedAt(LocalDateTime.now().minusMonths(1).plusDays(1));
//		item2.setPlan(plan);
//		itemRepository.save(item2);
//
//		JpaPlanItem item3 = new JpaPlanItem();
//		item3.setTitle("Vorhänge waschen");
//		item3.setType(ItemType.TIMED);
//		item3.setDuration(Duration.ofMinutes(30));
//		item3.setTimeInterval(Period.ofMonths(1));
//		item3.setCheckedAt(LocalDateTime.now().minusDays(1));
//		item3.setPlan(plan);
//		itemRepository.save(item3);
//
//		JpaPlanItem item5 = new JpaPlanItem();
//		item5.setTitle("Gemüse schneiden");
//		item5.setType(ItemType.TIMED);
//		item5.setDuration(Duration.ofMinutes(30));
//		item5.setTimeInterval(Period.ofMonths(1));
//		item5.setCheckedAt(LocalDateTime.now().minusDays(3));
//		item5.setPlan(plan);
//		itemRepository.save(item5);
//
//		JpaPlanItem item4 = new JpaPlanItem();
//		item4.setTitle("Hundefutter einweichen");
//		item4.setType(ItemType.TIMED);
//		item4.setDuration(Duration.ofMinutes(60));
//		item4.setTimeInterval(Period.ofMonths(1));
//		item4.setCheckedAt(LocalDateTime.now().minusMonths(1).minusDays(1));
//		item4.setPlan(plan);
//		itemRepository.save(item4);
//
//		JpaPlanItem item6 = new JpaPlanItem();
//		item6.setTitle("Zähne putzen");
//		item6.setType(ItemType.TIMED);
//		item6.setDuration(Duration.ofMinutes(1));
//		item6.setTimeInterval(Period.ofDays(1));
//		item6.setCheckedAt(LocalDateTime.now().minusHours(1));
//		item6.setPlan(plan);
//		itemRepository.save(item6);
//
//		JpaPlanItem item7 = new JpaPlanItem();
//		item7.setTitle("Gesicht waschen");
//		item7.setType(ItemType.TIMED);
//		item7.setDuration(Duration.ofMinutes(3));
//		item7.setTimeInterval(Period.ofDays(1));
//		item7.setCheckedAt(LocalDateTime.now().minusHours(13));
//		item7.setPlan(plan);
//		itemRepository.save(item7);
//
//		JpaPlanItem item8 = new JpaPlanItem();
//		item8.setTitle("Zahnseide");
//		item8.setType(ItemType.TIMED);
//		item8.setDuration(Duration.ofMinutes(3));
//		item8.setTimeInterval(Period.ofDays(1));
//		item8.setCheckedAt(LocalDateTime.now().minusHours(23));
//		item8.setPlan(plan);
//		itemRepository.save(item8);
//	}
}
