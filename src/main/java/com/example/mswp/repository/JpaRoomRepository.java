package com.example.mswp.repository;

import com.example.mswp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRoomRepository extends JpaRepository<Room, Integer> {

    //Entity Id String
    List<Room> getById(String user);

    //채팅방 생성
    @Query("select max(number) from Room")
    Integer findMaxNumber();

    void deleteByNumber(int number);
}
