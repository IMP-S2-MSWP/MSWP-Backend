package com.example.mswp.service;

import com.example.mswp.dto.RoomDto;
import com.example.mswp.entity.Room;
import com.example.mswp.entity.User;
import com.example.mswp.repository.RoomRepository;

import java.util.*;

import com.example.mswp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final UserRepository userRepository;

    int number;

    Map<String, Object> res = new HashMap<>();

    // 사용자 id 기준 접속 가능한 방 목록
    public List<Room> roomList(RoomDto roomDto) {

        return roomRepository.getById(roomDto.getId());

    }

    // 채팅방 생성
    @Transactional
    public Map createRoom(RoomDto roomDto) {

        //성공
        if(roomDto.getSc() == 200) {
            //방 번호 Front에서 주는 거 저장하는 코드로 수정
            if(!roomDto.getIdList().isEmpty() || roomDto.getIdList().size() == 1) {

                for(int i = 0; i < roomDto.getIdList().size(); i++) {

                    if(userRepository.findById(roomDto.getIdList().get(i)).equals(Optional.empty())) {
                        res.put("sc", 400);
                        return res;
                    }
                }

                if(roomRepository.findMaxNumber() == null) {
                    number = 1;
                } else {
                    number = roomRepository.findMaxNumber() + 1;
                }

                for (int i = 0; i < roomDto.getIdList().size(); i++) {
                    roomDto.setNumber(number);
                    Room room = roomDto.toEntity(i);
                    roomRepository.save(room);
                }

                res.put("sc", 200);
                res.put("number", number);
                res.put("idList", roomDto.getIdList());

                return res;

            } else {
                return Collections.emptyMap();
            }
        } else {
            deleteRoom();
            res.put("sc", 400);
            return res;
        }

    }

    @Transactional // 여러 데이터베이스 연산을 하나의 논리적인 작업 단위로 묶음 (일관성 유지 위함)
    public void deleteRoom() {
        roomRepository.deleteByNumber(number);
    }
}
