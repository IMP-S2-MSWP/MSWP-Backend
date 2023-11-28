package com.example.mswp.repository;

import com.example.mswp.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaLikesRepository extends JpaRepository<Likes, Integer> {

    Optional<Likes> findByIdToAndIdFrom(String idTo, String idFrom);

    List<Likes> findByIdFromAndExpiredAtIsNull(String id);

    List<Likes> findByIdToAndExpiredAtIsNull(String id);

}
