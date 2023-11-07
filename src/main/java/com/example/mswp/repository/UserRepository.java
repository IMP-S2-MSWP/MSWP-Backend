package com.example.mswp.repository;

import com.example.mswp.entity.User;
import com.example.mswp.mapping.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByIdAndPassword(String id, String password);

    Optional<UserMapping> findAllById(String id);

    User findUserByUuid(String uuid);

}
