package com.example.eventstask.Service;

import com.example.eventstask.Api.ApiException;
import com.example.eventstask.Model.Events;
import com.example.eventstask.Repository.EventsRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsService {
    private final EventsRepositry eventsRepositry;

    public List<Events> getAllEvents(){
        return eventsRepositry.findAll();
    }

    public Events getEventByIdService(Integer event_id){
        Events getEvent = eventsRepositry.findEventsById(event_id);
        if(getEvent == null)
            throw new ApiException("Sorry Event not Found");
        return getEvent;
    }

    public void addEvent(Events events){
        if(events.getDate().isBefore(LocalDate.now()))
            throw new ApiException("Can't added this event since date in the past");
//        else if (events.getEndTime().isBefore(LocalTime.now())) {
//            throw new ApiException("Can't added this event since date in the past");
//        }
        eventsRepositry.save(events);
    }

    public void updateEvents(Integer event_id,Events events){
        Events oldEvent = eventsRepositry.findEventsById(event_id);

        if (oldEvent == null)
            throw new ApiException("Event id is wrong!");

        oldEvent.setName(events.getName());
        oldEvent.setLocation(events.getLocation());
        oldEvent.setDescription(events.getDescription());
        oldEvent.setAttendees(oldEvent.getAttendees());
        oldEvent.setDate(events.getDate());
        oldEvent.setStartTime(events.getStartTime());
        oldEvent.setEndTime(events.getEndTime());

        eventsRepositry.save(oldEvent);
    }

    public void deleteEvents(Integer event_id){
        Events deleteEvent = eventsRepositry.findEventsById(event_id);

        if (deleteEvent == null)
            throw new ApiException("Event id is wrong");

        eventsRepositry.delete(deleteEvent);
    }
}
