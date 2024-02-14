package com.example.eventstask.Service;

import com.example.eventstask.Api.ApiException;
import com.example.eventstask.Model.Events;
import com.example.eventstask.Model.Users;
import com.example.eventstask.Repository.EventsRepositry;
import com.example.eventstask.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final EventsRepositry eventsRepositry;

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    public Users getUserById(Integer id){
        Users getUser = usersRepository.findUsersById(id);
        if (getUser == null)
            throw new ApiException("User Not found");

        return getUser;
    }
    public void addUser(Users users){
        usersRepository.save(users);
    }

    public void updateUser(Integer id, Users users){
        Users oldUsers = usersRepository.findUsersById(id);

        if (oldUsers == null)
            throw new ApiException("Sorry the user id is wrong!");
        oldUsers.setName(users.getName());

        usersRepository.save(oldUsers);
    }

    public void deleteUser(Integer id){
        Users deleteUser = usersRepository.findUsersById(id);

        if (deleteUser == null)
            throw new ApiException("Sorry the user id is wrong!");
        deleteUser.setEvents(null);
        usersRepository.save(deleteUser);

        usersRepository.delete(deleteUser);
    }

    public void assignUserToEvent(Integer user_id, Integer event_id){
        Users users = usersRepository.findUsersById(user_id);
        Events event = eventsRepositry.findEventsById(event_id);

        if (users == null|| event == null)
            throw new ApiException("Sorry can't find the user or event");

        if (!(event.getUsers() ==null)){
            for (int i = 0; i < event.getUsers().size(); i++){ // a user should not be added twice
                if (event.getUsers().get(i).getId() == user_id)
                    throw new ApiException("You already enrolled to this event");
            }
        }

        if (!(users.getEvents()== null)){
            for (int i =0 ; i < users.getEvents().size() ; i++){ // a user should not be added to an event cross with other event
                Events currentEvent = eventsRepositry.findEventsById(users.getEvents().get(i).getId());

                if(event.getDate().isEqual(currentEvent.getDate())){
                    if (event.getStartTime().equals(currentEvent.getStartTime())
                            || (event.getStartTime().isAfter(currentEvent.getStartTime()) && event.getEndTime().isBefore(currentEvent.getEndTime()))
                            || (event.getStartTime().isBefore(currentEvent.getEndTime()) && event.getEndTime().isAfter(currentEvent.getEndTime())
                            || (event.getStartTime().isBefore(currentEvent.getStartTime()) && event.getEndTime().isAfter(currentEvent.getStartTime())) ) )

                        throw new ApiException("You Can't enroll to this event because it conflict with : "+ currentEvent.getName());
                }
            }
        }

        if(event.getDate().isEqual(LocalDate.now()) && event.getStartTime().isBefore(LocalTime.now())) // a user should not be added to an event past it's start time
            throw new ApiException("Sorry you can't enroll to the "+event.getName() +" because it started");


        users.getEvents().add(event);

        usersRepository.save(users);
    }

    public void UserDeleteEvent(Integer user_id, Integer event_id){
        Users users = usersRepository.findUsersById(user_id);
        Events event = eventsRepositry.findEventsById(event_id);

        if (users == null|| event == null)
            throw new ApiException("Sorry can't find the user or event");

        for (int i = 0; i < users.getEvents().size(); i++){
            if (users.getEvents().get(i).getId() == event_id){
                users.getEvents().remove(event);
            }
        }
        usersRepository.save(users);
    }
}
