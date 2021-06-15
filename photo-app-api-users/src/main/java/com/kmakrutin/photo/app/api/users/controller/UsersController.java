package com.kmakrutin.photo.app.api.users.controller;

import com.kmakrutin.photo.app.api.users.dto.UserDto;
import com.kmakrutin.photo.app.api.users.model.CreateUserRequestModel;
import com.kmakrutin.photo.app.api.users.model.CreateUserResponseModel;
import com.kmakrutin.photo.app.api.users.model.UserResponseModel;
import com.kmakrutin.photo.app.api.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private final Environment environment;
    @Autowired
    private final UserService userService;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + environment.getProperty("local.server.port") +
                ". Token: " + environment.getProperty("token.secret") +
                "\n Owner: " + environment.getProperty("users.ws.owner", "Unknown");
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        CreateUserResponseModel responseModel = modelMapper.map(userService.create(userDto), CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
        UserDto userDto = userService.getByUserId(userId);

        return ResponseEntity.ok(new ModelMapper().map(userDto, UserResponseModel.class));
    }
}
