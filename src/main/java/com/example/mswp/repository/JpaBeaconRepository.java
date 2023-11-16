package com.example.mswp.repository;
import com.example.mswp.entity.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBeaconRepository extends JpaRepository<Beacon, Integer> {
    List<Beacon> getById(String user);

    Beacon findUserByUuid(String uuid);
}
