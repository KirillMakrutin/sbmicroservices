package com.kmakrutin.photo.app.api.users.service;

import com.kmakrutin.photo.app.api.users.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto create(UserDto userDetails);

    UserDto getUserDetailsByEmail(String email);
}
