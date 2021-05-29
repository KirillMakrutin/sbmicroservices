package com.kmakrutin.photo.app.api.users.service;

import com.kmakrutin.photo.app.api.users.dto.UserDto;

public interface UserService {
    UserDto create(UserDto userDetails);
}
