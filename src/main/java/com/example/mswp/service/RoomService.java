package com.example.mswp.service;

import com.example.mswp.dto.RoomDto;
import com.example.mswp.dto.RoomListDto;
import com.example.mswp.entity.Room;
import com.example.mswp.repository.JpaRoomRepository;
import com.example.mswp.repository.JpaUserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RoomService {

    @Autowired
    private final JpaRoomRepository jpaRoomRepository;
    @Autowired
    private final JpaUserRepository jpaUserRepository;

    int number;

    Map<String, Object> res = new HashMap<>();

    // 사용자 id 기준 접속 가능한 방 목록
    public List<RoomListDto> roomList(RoomDto roomDto) {
        List<Room> roomlist = jpaRoomRepository.getById(roomDto.getId());
        List<String> testlist = new ArrayList<>();
        for (int i = 0 ; i < roomlist.size();i++){
            testlist.add(roomlist.get(i).getNumber());
            System.out.println(roomlist.get(i).getNumber());
        }
        return jpaRoomRepository.myRoomList(roomDto.getId(),testlist);
        //return jpaRoomRepository.findByIdAndState(roomDto.getId(),roomDto.getState());

    }

    // 채팅방 생성
    @Transactional
    public Map createRoom(RoomDto roomDto) {
        List<String> roomId = jpaRoomRepository.findRoomNumbersWithBothUsers(roomDto.getIdList());
        if (roomId.size() == 0){

            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy@MM@dd");
            String formatedNow = now.format(formatter);
            formatedNow = formatedNow.concat("@");


            for (int i = 0; i < roomDto.getIdList().size(); i++) {
                formatedNow = formatedNow.concat(roomDto.getIdList().get(i));
            }

            roomDto.setNumber(formatedNow);
            for (int i = 0; i < roomDto.getIdList().size(); i++){

                Room room = roomDto.toEntity(i);
                jpaRoomRepository.save(room);
            }

            String orderUser = jpaRoomRepository.findIdsByNumberAndNotUserId(formatedNow,roomDto.getIdList().get(0));
            String rname = jpaUserRepository.findNicknameById(orderUser);
            res.put("sc",201);
            res.put("number",formatedNow);
            res.put("id",orderUser);
            res.put("rname",rname);
            return res;
        }
        else {
            String orderUser = jpaRoomRepository.findIdsByNumberAndNotUserId(roomId.get(0),roomDto.getIdList().get(0));
            String rname = jpaUserRepository.findNicknameById(orderUser);
            System.out.println(orderUser);
            res.put("sc",200);
            res.put("number",roomId.get(0));
            res.put("id",orderUser);
            res.put("rname",rname);
            return res;
        }


    }

    @Transactional // 여러 데이터베이스 연산을 하나의 논리적인 작업 단위로 묶음 (일관성 유지 위함)
    public void deleteRoom() {
        jpaRoomRepository.deleteByNumber("test");
    }
}