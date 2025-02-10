package com.sportyShoes.service;

import com.sportyShoes.pojo.User;
import com.sportyShoes.util.UserRepo;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;

    public User createUser(User user) {
        if (user.getRole() == null) {
            user.setRole("user");
        }
        return repo.save(user);
    }

    public User checkUser(String email, String password) throws Exception {
        User user = repo.findUserByEmailAndPassword(email, password);
        if (user == null) {
            throw new Exception("User not found with email: " + email + " and password: " + password);
        } else {
            return user;
        }
    }

    public List<User> getUsers() {
        return repo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repo.findById(id);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    public Object updateUser(@PathVariable("id") Long id, @RequestBody User userDetails) {
        // Retrieve the user from the repository
        Optional<User> optionalUser = repo.findById(id);
        // If the user is found, update and return it, otherwise return null
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userDetails.getEmail() != null) {
                user.setEmail(userDetails.getEmail());
            }
            if (userDetails.getPassword() != null) {
                user.setPassword(userDetails.getPassword());
            }
            if (userDetails.getRole() != null) {
                user.setRole(userDetails.getRole());
            }
            repo.save(user);
            return user;
        } else {
            return "User not found.";
        }
    }
}
