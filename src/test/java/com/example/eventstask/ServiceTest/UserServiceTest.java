package com.example.eventstask.ServiceTest;

import com.example.eventstask.Api.ApiException;
import com.example.eventstask.Model.Events;
import com.example.eventstask.Model.Users;
import com.example.eventstask.Repository.EventsRepositry;
import com.example.eventstask.Repository.UsersRepository;
import com.example.eventstask.Service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UsersService usersService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    EventsRepositry eventsRepositry;

//    @Mock
//    ApiException apiException;

    Users user1,user2,user3;
    List<Users> users;

    Events event1,event2,event3;
    List<Events> events;

    @BeforeEach
    void setUp(){
        user1 = new Users(null , "Reham",null);
        user2 = new Users(null , "Sara",null);
        user3 = new Users(null , "Rahaf",null);

        event1 = new Events(null, "Event Test 1", "Riyadh", "Event Test Example",3, LocalDate.of(2024,2,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);
        event2 = new Events(null, "Event Test 2", "Riyadh", "Event Test Example",3, LocalDate.of(2024,3,20), LocalTime.of(14,00,00),LocalTime.of(18,00,00),null);
        event3 = new Events(null, "Event Test 3", "Riyadh", "Event Test Example",3, LocalDate.of(2024,4,22), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);


        users = new ArrayList<>();
        users.add(user2);
        users.add(user3);

        events = new ArrayList<>();
        events.add(event2);

        event1.setUsers(users);
        user1.setEvents(events);
    }

    @Test
    public void getAllUsersTest(){
        when(usersRepository.findAll()).thenReturn(users);
        List<Users> checkUsers = usersService.getAllUsers();
        Assertions.assertEquals(checkUsers,users);
        verify(usersRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getUserByIdServiceTest(){
        when(usersRepository.findUsersById(user1.getId())).thenReturn(user1);
        Users checkUser = usersService.getUserById(user1.getId());

        Assertions.assertEquals(checkUser, user1);

        verify(usersRepository, Mockito.times(1)).findUsersById(user1.getId());
    }

    @Test
    public void addUserServiceTest(){
        usersService.addUser(user1);
        verify(usersRepository, times(1)).save(user1);
    }

    @Test
    public void updateUserServiceTest(){
        when(usersRepository.findUsersById(user1.getId())).thenReturn(user1);
        user1.setName("Update user");
        usersService.updateUser(user1.getId(), user1);

        verify(usersRepository, times(1)).findUsersById(user1.getId());
    }

    @Test
    public void deleteUserServiceTest(){
        when(usersRepository.findUsersById(user1.getId())).thenReturn(user1);

        usersService.deleteUser(user1.getId());

        verify(usersRepository,Mockito.times(1)).findUsersById(user1.getId());

    }

    @Test
    public void assignUserToEventTest(){
        when(usersRepository.findUsersById(user1.getId())).thenReturn(user1);
        when(eventsRepositry.findEventsById(event1.getId())).thenReturn(event1);
        when(eventsRepositry.findEventsById(event2.getId())).thenReturn(event2);


        usersService.assignUserToEvent(user1.getId(), event1.getId());

        verify(eventsRepositry,times(2)).findEventsById(event2.getId());
        verify(eventsRepositry, times(2)).findEventsById(event1.getId());
        verify(usersRepository, times(1)).findUsersById(user1.getId());
        verify(usersRepository, times(1)).save(user1);

    }

    @Test
    public void userDeleteEventTest(){
        when(usersRepository.findUsersById(user1.getId())).thenReturn(user1);
        when(eventsRepositry.findEventsById(event2.getId())).thenReturn(event2);

        usersService.UserDeleteEvent(user1.getId(), event2.getId());

        verify(eventsRepositry,times(1)).findEventsById(event2.getId());
        verify(usersRepository,times(1)).findUsersById(user1.getId());
    }
}
