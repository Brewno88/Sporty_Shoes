package com.sportyShoes.controller;

import com.sportyShoes.dto.LoginRequest;
import com.sportyShoes.pojo.User;
import com.sportyShoes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PostMapping("/checkUser")
    public User checkUser(@RequestBody LoginRequest loginRequest) throws Exception {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (service.checkUser(email, password) != null) {
            return service.checkUser(email, password);
        } else {
            throw new Exception("User not found");
        }
    }

    @PutMapping("/updateUser/{id}")
    public Object updateUser(@PathVariable Long id, @RequestBody User userDetails) throws IOException {
        User user = new User();
        user.setId(id);
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        return service.updateUser(id, user);
    }

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/getUser/{id}")
    public Optional<User> getUserById(@PathVariable("id") Long id) {
        return service.getUserById(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        if (service.getUserById(id).isPresent()) {
            service.deleteUser(id);
            return "Deleted user with: " + id;
        }
        return "Couldn't find a User with id:" + id;
    }
}
