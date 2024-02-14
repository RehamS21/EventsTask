package com.example.eventstask.ControllerTest;

import com.example.eventstask.Controller.EventsController;
import com.example.eventstask.Model.Events;
import com.example.eventstask.Model.Users;
import com.example.eventstask.Service.EventsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EventsController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class EventControllerTest {

    @MockBean
    EventsService eventsService;

    @Autowired
    MockMvc mockMvc;

    Events event1;
    List<Users> users;

    List<Events> events;

    @BeforeEach
    void setUp(){
        Users user1 = new Users(null,"Reham Al",null);
        users = new ArrayList<>();
        users.add(user1);

        event1 = new Events(1, "Event Test 1", "Riyadh", "Event Test Example",3,LocalDate.of(2024,2,20),LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);
//        event2 = new Events(null, "Event Test 2", "Riyadh", "Event Test Example",3,LocalDate.of(2024,03,15),LocalTime.of(14,00,00),LocalTime.of(16,00,00),users);

        events = new ArrayList<>();
        event1.setUsers(users);
        events.add(event1);
    }

    @Test
    public void getAllEventsTest() throws Exception{
        Mockito.when(eventsService.getAllEvents()).thenReturn(events);
        mockMvc.perform(get("/api/events/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Event Test 1"));
    }


    @Test
    public void addEventTest() throws Exception{
        mockMvc.perform(post("/api/events/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(event1)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEventTest() throws Exception{
        event1.setName("update Event");
        mockMvc.perform(put("/api/events/update/{id}",event1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(event1)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEventTest() throws Exception{
        mockMvc.perform(delete("/api/events/delete/{id}",event1.getId()))
                .andExpect(status().isOk());
    }

}
