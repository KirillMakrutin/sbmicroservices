package com.kmakrutin.mobile.app.ws.controller;

import com.kmakrutin.mobile.app.ws.model.request.UserDetailsRequestModel;
import com.kmakrutin.mobile.app.ws.model.response.UserRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "users",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

    @GetMapping
    public Collection<UserRest> getUsers(@RequestParam(required = false) Integer page, @RequestParam(defaultValue = "100") int limit) {
        return List.of(userRest(), userRest(), userRest());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userRest());
    }

    @PostMapping
    public UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        return UserRest.builder()
                .id(UUID.randomUUID().toString())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .build();
    }

    @PutMapping
    public UserRest updateUser() {
        return userRest();
    }

    @DeleteMapping
    public UserRest deleteUser() {
        return userRest();
    }

    private UserRest userRest() {
        String id = UUID.randomUUID().toString();
        String firstName = id.substring(0, 10);
        String lastName = id.substring(10);
        String email = id.substring(0, 5) + "@gmail.com";

        return UserRest.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
    }
}
