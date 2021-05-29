package com.kmakrutin.photo.app.api.users.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    // public user id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
}
