package com.example.eventstask.RepositoryTest;

import com.example.eventstask.Model.Events;
import com.example.eventstask.Model.Users;
import com.example.eventstask.Repository.EventsRepositry;
import com.example.eventstask.Repository.UsersRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.annotations.Array;
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
public class UserRepositoryTest {
    @Autowired
    UsersRepository usersRepository;

    Users user1,user2;

    Events event;
    List<Events> events;

    @BeforeEach
    void setUp(){
        user1 = new Users(null, "Reham Sayer",null);
        event = new Events(null, "Event Test 1", "Riyadh", "Event Test Example",3, LocalDate.of(2024,2,20), LocalTime.of(14,00,00),LocalTime.of(16,00,00),null);

        events = Arrays.asList(event);
        user1.setEvents(events);

    }

    @Test
    public void findUserByID(){
        usersRepository.save(user1);

        user2 = usersRepository.findUsersById(user1.getId());
        Assertions.assertThat(user2).isEqualTo(user1);
    }


}
