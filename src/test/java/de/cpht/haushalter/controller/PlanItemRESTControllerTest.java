package de.cpht.haushalter.controller;

import de.cpht.haushalter.domain.entities.PlanItem;
import de.cpht.haushalter.domain.usecases.PlanUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanItemRESTController.class)
public class PlanItemRESTControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    private PlanUseCase planUseCase;

    @Test
    public void testIndex() throws Exception {
//        Plan plan = new Plan("Test Plan", "Test Description");
        PlanItem planItem = new PlanItem("Item Title", "Item Description", LocalDate.now(), Period.ofDays(1), Duration.ofMinutes(15));
        when(planUseCase.getItems(any())).thenReturn(List.of(planItem));
        mvc.perform(get("/plans/1/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value(planItem.title))
                .andExpect(jsonPath("$[0].description").value(planItem.description))
                .andExpect(jsonPath("$[0].startDate").value(planItem.startDate.toString()))
                .andExpect(jsonPath("$[0].timeInterval").value(planItem.timeInterval.toString()))
                .andExpect(jsonPath("$[0].duration").value(planItem.duration.toString()))
                .andDo(print());
    }

//    @Test
//    public void testStore() throws Exception {
//        ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> descriptionCaptor = ArgumentCaptor.forClass(String.class);
//        Plan plan = new Plan("Test Plan", "Test Description");
//        when(planUseCase.startPlan(titleCaptor.capture(), descriptionCaptor.capture())).thenReturn(plan);
//        mvc.perform(post("/plans")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(plan))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("title").value(titleCaptor.getValue()))
//                .andExpect(jsonPath("description").value(descriptionCaptor.getValue()))
//                .andDo(print());
//    }
//
//    @Test
//    public void testShow() throws Exception {
//        Plan plan = new Plan("Test Plan", "Test Description");
//        when(planUseCase.getPlanById(any())).thenReturn(plan);
//        mvc.perform(get("/plans/16"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("title").value("Test Plan"))
//                .andExpect(jsonPath("description").value("Test Description"))
//                .andDo(print());
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        ArgumentCaptor<Plan> planCaptor = ArgumentCaptor.forClass(Plan.class);
//        Plan plan = new Plan("Updated Plan", "Updated Description");
//        plan.isDefault = true;
//        plan.done = true;
//        when(planUseCase.updatePlan(any(), planCaptor.capture())).thenReturn(plan);
//        mvc.perform(put("/plans/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(plan))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("title").value(planCaptor.getValue().title))
//                .andExpect(jsonPath("description").value(planCaptor.getValue().description))
//                .andExpect(jsonPath("isDefault").value(planCaptor.getValue().isDefault))
//                .andExpect(jsonPath("done").value(planCaptor.getValue().done))
//                .andDo(print());
//    }
}
