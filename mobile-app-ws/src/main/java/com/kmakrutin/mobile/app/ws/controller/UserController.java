package com.kmakrutin.mobile.app.ws.controller;

import com.kmakrutin.mobile.app.ws.model.request.UpdateUserDetailsRequestModel;
import com.kmakrutin.mobile.app.ws.model.request.UserDetailsRequestModel;
import com.kmakrutin.mobile.app.ws.model.response.UserRest;
import com.kmakrutin.mobile.app.ws.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "users",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public Collection<UserRest> getUsers(@RequestParam(required = false) Integer page, @RequestParam(defaultValue = "100") int limit) {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {

        final UserRest user = userService.findById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        final UserRest newUser = UserRest.builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .build();

        return userService.save(newUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserRest> updateUser(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
        final UserRest userForUpdate = UserRest.builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .build();

        final UserRest updatedUser = userService.update(userId, userForUpdate);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<UserRest> deleteUser(@PathVariable String userId) {
        UserRest deletedUser = userService.delete(userId);

        if (deletedUser != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
