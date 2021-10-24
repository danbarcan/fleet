package com.web.dd.fleet.controller;

import com.web.dd.fleet.payload.SignUpPayload;
import com.web.dd.fleet.payload.UserPayload;
import com.web.dd.fleet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserPayload>> getAllCars() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserPayload> getCarById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity.HeadersBuilder deleteCar(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserPayload> updateCar(@PathVariable Long userId, @Valid @RequestBody SignUpPayload signUpPayload) {
        return userService.updateUser(userId, signUpPayload);
    }
}