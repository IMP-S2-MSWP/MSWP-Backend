package com.example.mswp.repository;

import com.example.mswp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


   Optional<User> findById(String id);
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByIdAndPassword(String id, String password);

}
