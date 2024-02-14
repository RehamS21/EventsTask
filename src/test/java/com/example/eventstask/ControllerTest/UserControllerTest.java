package com.example.eventstask.ControllerTest;

import com.example.eventstask.Controller.UsersController;
import com.example.eventstask.Model.Events;
import com.example.eventstask.Model.Users;
import com.example.eventstask.Service.EventsService;
import com.example.eventstask.Service.UsersService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;


import java.time.LocalDate;
import java.time.LocalTime;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UsersController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UserControllerTest {

    @MockBean
    UsersService usersService;
    @MockBean
    EventsService eventsService;

    @Autowired
    MockMvc mockMvc;

    Users user1;

    Events event1,event2;
    List<Users> users;

    List<Events> events;

    @BeforeEach
    void setUp(){
        user1 = new Users(null, "Reham Sayer",null);

        event1 = new Events(null, "Event Test 1", "Riyadh", "Event Test Example",3, LocalDate.of(2024,2,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);
        event2 = new Events(null, "Event Test 2", "Riyadh", "Event Test Example",3, LocalDate.of(2024,3,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);
        events = new ArrayList<>();

        events.add(event1);

//        user1.setEvents(events);
//
        users= new ArrayList<>();
        users.add(user1);
    }

    @Test
    public void getUsersTest() throws Exception{
        Mockito.when(usersService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(users.size()));
    }

    @Test
    public void getUserByIdTest() throws Exception{
        Mockito.when(usersService.getUserById(user1.getId())).thenReturn(user1);

        mockMvc.perform(get("/api/users/get/{user_id}",user1.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Reham Sayer"));
    }

    @Test
    public void addUserTest() throws Exception{
        mockMvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(user1)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserTest() throws Exception{
        user1.setName("Update Name");
        mockMvc.perform(put("/api/users/update/{id}",user1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(user1)))
                        .andExpect(status().isOk());
    }


    @Test
    public void deleteUsersTest() throws Exception{
        mockMvc.perform(delete("/api/users/delete/{id}",user1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void assignUserToEventTest() throws Exception {
        mockMvc.perform(put("/api/users/{user_id}/{event_id}",user1.getId(), event1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void userDeleteEvent() throws Exception{
        events.add(event1);
        user1.setEvents(events);
        mockMvc.perform(delete("/api/users/{user_id}/{event_id}",user1.getId(), event1.getId()))
                .andExpect(status().isOk());
    }

}
