package com.example.mswp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mswp.entity.Beacon;

import java.util.List;

@Repository
public interface JpaBeaconRepository extends JpaRepository<Beacon, Integer> {
    List<Beacon> getById(String user);

    Beacon findUserByUuid(String uuid);

    //test
    @Query("SELECT b FROM Beacon b WHERE b.uuid IN (SELECT a.number  FROM Room a WHERE a.id = :roomId AND a.state = :state)")
    List<Beacon> findBeaconsByRoomIdAndState(String roomId,Character state);
}
