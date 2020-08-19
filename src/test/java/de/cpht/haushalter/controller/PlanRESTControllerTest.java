package de.cpht.haushalter.controller;

import de.cpht.haushalter.model.Plan;
import de.cpht.haushalter.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanRESTController.class)
public class PlanRESTControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    private PlanRepository planRepository;

    @Test
    public void testPlanIndex() throws Exception {
        Plan plan = new Plan();
        plan.setTitle("Test Plan");
        plan.setDescription("Test Description");
        planRepository.save(plan);
        given(planRepository.findAll()).willReturn(List.of(plan));
        mvc.perform(get("/plans"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Plan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Test Description"))
                .andDo(print());
    }
}
