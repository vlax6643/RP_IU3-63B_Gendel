package ru.hendel.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.hendel.backend.domain.Museum;
import ru.hendel.backend.domain.User;
import ru.hendel.backend.repos.MuseumRepository;
import ru.hendel.backend.repos.UserRepository;
import ru.hendel.backend.tools.Utils;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MuseumRepository museumRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            // Генерация соли
            String salt = UUID.randomUUID().toString().substring(0, 8);
            user.setSalt(salt);

            // Шифрование пароля
            String rawPassword = user.getPassword();
            if (rawPassword != null && !rawPassword.isEmpty()) {
                String hashedPassword = Utils.ComputeHash(rawPassword, salt);
                user.setPassword(hashedPassword);
            }

            User newUser = userRepository.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            if (ex.getMessage().contains("users.login_UNIQUE")) {
                error.put("error", "loginexists");
            } else if (ex.getMessage().contains("users.email_UNIQUE")) {
                error.put("error", "emailexists");
            } else {
                error.put("error", "undefinederror");
            }
            return new ResponseEntity<>(error, HttpStatus.OK);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long userId,
                                             @RequestBody User userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setLogin(userDetails.getLogin());
        user.setEmail(userDetails.getEmail());
        // Обновляем только если пароль был предоставлен
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(userDetails.getPassword());
        }
        user.setSalt(userDetails.getSalt());
        user.setToken(userDetails.getToken());
        user.setActivity(userDetails.getActivity());

        try {
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            if (ex.getMessage().contains("users.login_UNIQUE")) {
                error.put("error", "loginexists");
            } else if (ex.getMessage().contains("users.email_UNIQUE")) {
                error.put("error", "emailexists");
            } else {
                error.put("error", "undefinederror");
            }
            return new ResponseEntity<>(error, HttpStatus.OK);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/users/{id}/museums")
    public Set<Museum> getUserMuseums(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getMuseums();
    }

    @PutMapping("/users/{userId}/museums/{museumId}")
    public ResponseEntity<Object> addMuseumToUser(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "museumId") Long museumId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Museum museum = museumRepository.findById(museumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Museum not found"));

        user.getMuseums().add(museum);
        userRepository.save(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("added", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{userId}/museums/{museumId}")
    public ResponseEntity<Object> removeMuseumFromUser(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "museumId") Long museumId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Museum museum = museumRepository.findById(museumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Museum not found"));

        user.getMuseums().remove(museum);
        userRepository.save(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("removed", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}