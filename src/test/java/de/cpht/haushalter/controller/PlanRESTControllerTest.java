package de.cpht.haushalter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cpht.haushalter.domain.entities.Plan;
import de.cpht.haushalter.domain.entities.PlanType;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testIndex() throws Exception {
        Plan plan = new Plan("Test Plan", "Test Description", PlanType.CHECKLIST);
        when(planUseCase.showAllPlans(PlanType.CHECKLIST)).thenReturn(List.of(plan));
        mvc.perform(get("/plans?type=CHECKLIST").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("Test Plan"))
                .andExpect(jsonPath("$[0].description").value("Test Description"))
                .andDo(print());
    }

    @Test
    public void testStore() throws Exception {
        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<PlanType> typeCaptor = ArgumentCaptor.forClass(PlanType.class);
        Plan plan = new Plan("Test Plan", "Test Description", PlanType.CHECKLIST);
        when(planUseCase.startPlan(titleCaptor.capture(), descriptionCaptor.capture(), typeCaptor.capture())).thenReturn(plan);
        mvc.perform(post("/plans?type=CHECKLIST")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(plan))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("title").value(titleCaptor.getValue()))
                .andExpect(jsonPath("description").value(descriptionCaptor.getValue()))
                .andExpect(jsonPath("type").value(typeCaptor.getValue().name()))
                .andDo(print());
    }

    @Test
    public void testShow() throws Exception {
        Plan plan = new Plan("Test Plan", "Test Description", PlanType.CHECKLIST);
        when(planUseCase.getPlanById(any())).thenReturn(plan);
        mvc.perform(get("/plans/16"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("title").value("Test Plan"))
                .andExpect(jsonPath("description").value("Test Description"))
                .andDo(print());
    }

    @Test
    public void testUpdate() throws Exception {
        ArgumentCaptor<Plan> planCaptor = ArgumentCaptor.forClass(Plan.class);
        Plan plan = new Plan("Updated Plan", "Updated Description", PlanType.CHECKLIST);
        plan.type = PlanType.DEFAULT;
        plan.done = true;
        when(planUseCase.updatePlan(any(), planCaptor.capture())).thenReturn(plan);
        mvc.perform(put("/plans/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(plan))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("title").value(planCaptor.getValue().title))
                .andExpect(jsonPath("description").value(planCaptor.getValue().description))
                .andExpect(jsonPath("type").value(planCaptor.getValue().type.name()))
                .andExpect(jsonPath("done").value(planCaptor.getValue().done))
                .andDo(print());
    }
}
