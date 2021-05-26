package com.kmakrutin.mobile.app.ws.service;

import com.kmakrutin.mobile.app.ws.model.response.UserRest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private final Map<String, UserRest> users = new HashMap<>();

    public UserRest save(UserRest user) {
        user.setId(UUID.randomUUID().toString());
        users.put(user.getId(), user);

        return user;
    }

    public UserRest update(String userId, UserRest user) {
        if (users.containsKey(userId)) {
            final UserRest userExisting = users.get(userId);
            userExisting.setFirstName(user.getFirstName());
            userExisting.setLastName(user.getLastName());

            return userExisting;
        }

        return null;
    }

    public UserRest findById(String id) {
        return users.get(id);
    }

    public Collection<UserRest> findAll() {
        return users.values();
    }

    public UserRest delete(String userId) {
        return users.remove(userId);
    }
}
