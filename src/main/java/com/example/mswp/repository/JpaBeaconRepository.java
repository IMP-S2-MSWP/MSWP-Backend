package com.example.mswp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mswp.entity.Beacon;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaBeaconRepository extends JpaRepository<Beacon, String> {
    //Beacon getById(String user);

    List<Beacon> findByCreator(String creator);

    Beacon findUserByUuid(String uuid);

    Optional<Beacon> findByUuid(String uuid);
}
