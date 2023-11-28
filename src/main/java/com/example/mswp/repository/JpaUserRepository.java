package com.example.mswp.repository;

import com.example.mswp.entity.User;
import com.example.mswp.mapping.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, String> {
    User findByIdAndPassword(String id, String password);

    Optional<UserMapping> findAllById(String id);

    User findUserByUuid(String uuid);
    @Query("SELECT nickname FROM User WHERE id = :id")
    String findNicknameById(@Param("id") String id);

}
