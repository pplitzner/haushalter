package de.cpht.haushalter.controller;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.repository.PlanRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanController.class)
public class PlanControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    private PlanRepository planRepository;

    @Test
    public void testPlanIndex() throws Exception {
        Plan plan = new Plan("Test Plan" , "Test Description");
        given(planRepository.findAll()).willReturn(List.of(plan));
        mvc.perform(get("/plans"))
                .andExpect(status().isOk())
                .andExpect(view().name("plans"))
                .andExpect(model().attributeExists("plans"))
                .andExpect(model().attribute("plans", Matchers.contains(plan)));
    }
}
