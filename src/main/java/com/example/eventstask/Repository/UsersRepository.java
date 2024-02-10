package com.example.eventstask.Repository;

import com.example.eventstask.Model.Events;
import com.example.eventstask.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findUsersById(Integer id);

//    List<Events> findUsersByEvents();
}
