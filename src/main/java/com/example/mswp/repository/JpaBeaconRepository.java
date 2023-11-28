package com.example.mswp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    //test
    @Query("SELECT b FROM Beacon b WHERE b.uuid IN (SELECT a.number  FROM Room a WHERE a.id = :id AND a.state = :state)")
    List<Beacon> findBeaconsByRoomIdAndState(String id,Character state);

}
