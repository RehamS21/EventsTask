package com.example.eventstask.Controller;

import com.example.eventstask.Model.Users;
import com.example.eventstask.Service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/get")
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping("/get/{user_id}")
    public Users getUser(@PathVariable Integer user_id){
        return usersService.getUserById(user_id);
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid Users users){
        usersService.addUser(users);
        return ResponseEntity.status(200).body("User added successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@RequestBody @Valid Users users, @PathVariable Integer id){
        usersService.updateUser(id,users);
        return ResponseEntity.status(200).body("User updated successfully");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        usersService.deleteUser(id);
        return ResponseEntity.status(200).body("User Deleted Successfully");
    }

    @PutMapping("/{user_id}/{event_id}")
    public ResponseEntity assignUserToEvent(@PathVariable Integer user_id, @PathVariable Integer event_id){
        usersService.assignUserToEvent(user_id,event_id);
        return ResponseEntity.status(200).body("User assigned to event successfully");
    }

    @DeleteMapping("/{user_id}/{event_id}")
    public ResponseEntity userDeleteEvent(@PathVariable Integer user_id, @PathVariable Integer event_id){
        usersService.UserDeleteEvent(user_id,event_id);
        return ResponseEntity.status(200).body("Event deleted successfully");
    }
}
