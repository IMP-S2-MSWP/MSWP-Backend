package com.example.mswp.repository;

import com.example.mswp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRoomRepository extends JpaRepository<Room, Integer> {

    List<Room> getById(String user);
    //Entity Id String
    List<Room> getByIdAndState(String user,Character state);

    //채팅방 생성
    @Query("select max(sequence) from Room")
    Integer findMaxNumber();

    void deleteByNumber(String number);
    @Query("SELECT number FROM Room WHERE id IN :ids AND state = '1' GROUP BY number HAVING COUNT(id) = 2")
    List<String> findRoomNumbersWithBothUsers(List<String> ids);

    @Query("SELECT id FROM Room WHERE number = :number AND id != :userId")
    String findIdsByNumberAndNotUserId(String number, String userId);


    Room findByNumberAndId(String number, String userId);
}