package com.kmakrutin.photo.app.api.users.repository;

import com.kmakrutin.photo.app.api.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findFirstByEmail(String email);

    Optional<UserEntity> findFirstByUserId(String userId);
}
