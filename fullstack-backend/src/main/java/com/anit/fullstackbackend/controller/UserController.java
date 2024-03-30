package com.anit.fullstackbackend.controller;

import com.anit.fullstackbackend.exception.UserNotFoundException;
import com.anit.fullstackbackend.model.User;
import com.anit.fullstackbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/saveUser")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @PostMapping("/saveUsers")
    List<User> createNewUsers(@RequestBody List<User> saveUserLists) {
        return userRepository.saveAll(saveUserLists);
    }

    @GetMapping("/getUsers")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUserById/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/editUser/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/deleteUser/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        return "User with id : " + id + " has been deleted.";
    }
}
