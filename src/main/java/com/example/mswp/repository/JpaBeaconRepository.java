package com.example.mswp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mswp.entity.Beacon;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaBeaconRepository extends JpaRepository<Beacon, String> {

    List<Beacon> findByCreator(String creator);

    Beacon findUserByUuid(String uuid);

    Optional<Beacon> findByUuid(String uuid);

    //test
    @Query("SELECT b FROM Beacon b WHERE b.uuid IN (SELECT a.number  FROM Room a WHERE a.id = :id AND a.state = :state)ORDER BY b.update_at DESC")
    List<Beacon> findBeaconsByRoomIdAndState(@Param("id")String id, @Param("state")Character state);

}
