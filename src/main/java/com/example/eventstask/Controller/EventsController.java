package com.example.eventstask.Controller;

import com.example.eventstask.Model.Events;
import com.example.eventstask.Service.EventsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventsController {
    private final EventsService eventsService;

    @GetMapping("/get")
    public List<Events> getAllEventsController(){
       return eventsService.getAllEvents();
    }

    @GetMapping("/get/{event_id}")
    public Events getEventsByIdController(@PathVariable Integer event_id){
        return eventsService.getEventByIdService(event_id);
    }
    @PostMapping("/add")
    public ResponseEntity addEventsController(@RequestBody @Valid Events events){
        eventsService.addEvent(events);
        return ResponseEntity.status(200).body("Event added successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateEventsController(@PathVariable Integer id, @RequestBody @Valid Events events){
        eventsService.updateEvents(id,events);
        return ResponseEntity.status(200).body("Event updated Successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEventsController(@PathVariable Integer id){
        eventsService.deleteEvents(id);
        return ResponseEntity.status(200).body("Event Deleted Successfully");

    }
}
