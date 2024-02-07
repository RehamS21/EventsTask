package com.example.eventstask.Repository;

import com.example.eventstask.Model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepositry extends JpaRepository<Events, Integer> {

    Events findEventsById(Integer id);
}
