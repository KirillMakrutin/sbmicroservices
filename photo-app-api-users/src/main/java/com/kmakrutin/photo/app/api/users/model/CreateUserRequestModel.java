package com.kmakrutin.photo.app.api.users.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequestModel {
    @NotNull(message = "First name is required")
    @Size(min = 2, message = "First name should be gte 2 chars")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 2, message = "Last name should be gte 2 chars")
    private String lastName;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotNull(message = "First name is required")
    @Size(min = 8, max = 16, message = "Password should be gte 8 and lte 16 chars")
    private String password;
}
