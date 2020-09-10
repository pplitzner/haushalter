package de.cpht.haushalter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanRESTController.class)
public class PlanRESTControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    private PlanUseCase planUseCase;

    @Test
    public void testPlanIndex() throws Exception {
        Plan plan = new Plan("Test Plan", "Test Description");
        given(planUseCase.showAllPlans()).willReturn(List.of(plan));
        mvc.perform(get("/plans").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("Test Plan"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andDo(print());
    }

    @Test
    public void testStartPlan() throws Exception {
        Plan plan = new Plan("Test Plan", "Test Description");
        when(planUseCase.startPlan(any(String.class), any(String.class))).thenReturn(plan);
        mvc.perform(post("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Plan("title", "description")))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").value("Test Plan"))
                .andExpect(jsonPath("description").value("Test Description"))
                .andDo(print());
    }
}
