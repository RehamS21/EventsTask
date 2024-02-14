package com.example.eventstask.RepositoryTest;

import com.example.eventstask.Model.Events;
import com.example.eventstask.Repository.EventsRepositry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTest {

    @Autowired
    EventsRepositry eventsRepositry;

    Events event1,event2;



    @BeforeEach
    void setUp(){
        event1 = new Events(null, "Event Test 1", "Riyadh", "Event Test Example",3, LocalDate.of(2024,2,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);
    }


    @Test
    public void findEventByIdTest(){
        eventsRepositry.save(event1);

        event2 = eventsRepositry.findEventsById(event1.getId());
        Assertions.assertThat(event2).isEqualTo(event1);
    }
}
