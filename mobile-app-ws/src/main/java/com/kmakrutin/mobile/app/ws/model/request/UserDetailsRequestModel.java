package com.kmakrutin.mobile.app.ws.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDetailsRequestModel {
    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email address is required")
    @Email
    private String email;

    @Size(min = 8, max = 16, message = "Password should be gt 8 and lt 16")
    @NotNull(message = "Password is required")
    private String password;
}
