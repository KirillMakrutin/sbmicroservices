package com.kmakrutin.photo.app.api.users.service;

import com.kmakrutin.photo.app.api.users.dto.UserDto;
import com.kmakrutin.photo.app.api.users.entity.UserEntity;
import com.kmakrutin.photo.app.api.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        userRepository.save(userEntity);

        return userDetails;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return userRepository.findFirstByEmail(email)
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .orElseThrow(() -> new UsernameNotFoundException("User not found by " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(email)
                .map(userEntity -> new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, Collections.emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found by " + email));
    }
}
