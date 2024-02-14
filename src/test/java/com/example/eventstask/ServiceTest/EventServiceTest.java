package com.example.eventstask.ServiceTest;

import com.example.eventstask.Model.Events;
import com.example.eventstask.Model.Users;
import com.example.eventstask.Repository.EventsRepositry;
import com.example.eventstask.Service.EventsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    EventsService eventsService;

    @Mock
    EventsRepositry eventsRepositry;

    Users user;

    Events event1,event2,event3;

    List<Events> events;

    @BeforeEach
    void setUp(){
        user = new Users(null , "Reham",null);
        event1 = new Events(null, "Event Test 1", "Riyadh", "Event Test Example",3, LocalDate.of(2024,2,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);
        event2 = new Events(null, "Event Test 2", "Riyadh", "Event Test Example",3, LocalDate.of(2024,3,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);
        event3 = new Events(null, "Event Test 3", "Riyadh", "Event Test Example",3, LocalDate.of(2024,4,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);


        events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        events.add(event3);

        user.setEvents(events);
    }

    @Test
    public void getEventServiceTest(){
        when(eventsRepositry.findAll()).thenReturn(events);
        List<Events> checkEvents = eventsService.getAllEvents();
        Assertions.assertEquals(checkEvents,events);

        Mockito.verify(eventsRepositry, Mockito.times(1)).findAll();
    }

    @Test
    public void getEventByIdServiceTest(){
        when(eventsRepositry.findEventsById(event1.getId())).thenReturn(event1);
        Events checkEvent = eventsService.getEventByIdService(event1.getId());
        Assertions.assertEquals(checkEvent,event1);

        verify(eventsRepositry,Mockito.times(1)).findEventsById(event1.getId());
    }
    @Test
    public void addEventServiceTest(){
        eventsService.addEvent(event1);
        verify(eventsRepositry, Mockito.times(1)).save(event1);
    }

    @Test
    public void updateEventServiceTest(){
        when(eventsRepositry.findEventsById(event1.getId())).thenReturn(event1);
        event1.setName("Update event1");
        event1.setLocation("Dmmam");

        eventsService.updateEvents(event1.getId(),event1);

        verify(eventsRepositry, Mockito.times(1)).findEventsById(event1.getId());
    }

    @Test
    public void deleteEventServiceTest(){
        when(eventsRepositry.findEventsById(event1.getId())).thenReturn(event1);

        eventsService.deleteEvents(event1.getId());

        verify(eventsRepositry,Mockito.times(1)).findEventsById(event1.getId());
    }
}
