package com.kmakrutin.photo.app.api.users.service;

import com.kmakrutin.photo.app.api.users.dto.UserDto;
import com.kmakrutin.photo.app.api.users.entity.UserEntity;
import com.kmakrutin.photo.app.api.users.model.AlbumResponseModel;
import com.kmakrutin.photo.app.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private final Environment environment;

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
    public UserDto getByUserId(String userId) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        String albumsUrl = environment.getProperty("albums.url", "");

        final ResponseEntity<List<AlbumResponseModel>> responseEntity = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
        }, userId);

        final UserDto userDto = userRepository.findFirstByUserId(userId)
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .orElseThrow(() -> new RuntimeException("User not found by id " + userId));

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            userDto.setAlbums(responseEntity.getBody());
        }

        return userDto;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(email)
                .map(userEntity -> new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, Collections.emptyList()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found by " + email));
    }
}
