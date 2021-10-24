package com.web.dd.fleet.service;

import com.web.dd.fleet.entity.User;
import com.web.dd.fleet.payload.SignUpPayload;
import com.web.dd.fleet.payload.UserPayload;
import com.web.dd.fleet.repository.UserRepository;
import com.web.dd.fleet.security.UserPrincipal;
import com.web.dd.fleet.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<UserPayload>> getAllUsers() {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(mapListOfUsersToResponse(userRepository.findAll()));
    }

    public ResponseEntity<UserPayload> getUserById(Long userId) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(UserPayload.createUserPayloadFromUser(userRepository.findById(userId).get()));
    }

    public ResponseEntity<UserPayload> updateUser(Long userId, SignUpPayload userPayload) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        Optional<User> userOptional = userRepository.findById(userPrincipal.getId());
        if (!userOptional.isPresent() || !userOptional.get().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = userOptional.get();

        user.setEmail(userPayload.getEmail());
        user.setName(userPayload.getName());
        user.setPassword(userPayload.getPassword());
        user.setUsername(userPayload.getUsername());
        user.setPhoneNumber(userPayload.getPhoneNumber());
        user.setEmailNotification(userPayload.getEmailNotification());
        user.setSmsNotification(userPayload.getSmsNotification());

        userRepository.save(user);

        return ResponseEntity.ok(UserPayload.createUserPayloadFromUser(user));
    }

    public ResponseEntity.HeadersBuilder<?> deleteUser(Long userId) {
        UserPrincipal userPrincipal = AppUtils.getCurrentUserDetails();
        if (!userPrincipal.getId().equals(userId)) {
            return ResponseEntity.badRequest();
        }
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound();
        }

        User user = userOptional.get();


        userRepository.delete(user);

        return ResponseEntity.noContent();
    }

    private List<UserPayload> mapListOfUsersToResponse(List<User> users) {
        return users.stream().map(UserPayload::createUserPayloadFromUser).collect(Collectors.toList());
    }
}
