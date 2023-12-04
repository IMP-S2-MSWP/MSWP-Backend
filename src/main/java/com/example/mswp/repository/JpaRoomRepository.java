package com.example.mswp.repository;

import com.example.mswp.entity.Room;
import com.example.mswp.entity.User;
import com.example.mswp.dto.RoomListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRoomRepository extends JpaRepository<Room, Integer> {

    //Entity id String
    List<Room> findByIdAndState(String user,Character state);

    //채팅방 생성
    @Query("select max(sequence) from Room")
    Integer findMaxNumber();

    void deleteByNumber(String number);
    @Query("SELECT number FROM Room WHERE id IN :ids AND state = '1' GROUP BY number HAVING COUNT(id) = 2")
    List<String> findRoomNumbersWithBothUsers(@Param("ids") List<String> ids);

    @Query("SELECT id FROM Room WHERE number = :number AND id != :userId")
    String findIdsByNumberAndNotUserId(@Param("number") String number, @Param("userId") String userId);


    Room findByNumberAndId(String number, String userId);
    @Query("SELECT new com.example.mswp.dto.RoomListDto(r.number, r.id, u.nickname) FROM Room r JOIN User u ON r.id = u.id WHERE r.id != :id and r.number in :rids")
    List<RoomListDto> myRoomList(@Param("id") String id,@Param("rids") List<String> rids);



}