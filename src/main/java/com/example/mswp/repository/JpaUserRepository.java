package com.example.mswp.repository;

import com.example.mswp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, String> {

    User findUserByUuid(String uuid);
    @Query("SELECT nickname FROM User WHERE id = :id")
    String findNicknameById(@Param("id") String id);

}
