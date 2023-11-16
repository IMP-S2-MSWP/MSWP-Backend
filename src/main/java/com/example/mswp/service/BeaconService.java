package com.example.mswp.service;

import com.example.mswp.dto.BeaconDto;
import com.example.mswp.entity.Room;
import com.example.mswp.entity.Beacon;
import com.example.mswp.repository.JpaBeaconRepository;
import com.example.mswp.dto.RoomDto;
import java.util.*;

import com.example.mswp.repository.JpaRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BeaconService {

    @Autowired
    private final JpaBeaconRepository jpaBeaconRepository;
    @Autowired
    private final JpaRoomRepository jpaRoomRepository;
    Map<String, Object> res = new HashMap<>();
    public Map createBeacon(BeaconDto beaconDto){
        Beacon beacon = jpaBeaconRepository.findUserByUuid(beaconDto.getUuid());

        if(beacon ==null){
            beacon = beaconDto.toEntity();
            jpaBeaconRepository.save(beacon);
            res.put("sc","200");
        }
        else{ //이미 해당 uuid가 등록되어있음
            res.put("sc","400");
        }
        return res;
    };

    //완
    public List<Beacon> myBeaconList (BeaconDto beaconDto){
        return jpaBeaconRepository.getById(beaconDto.getId());
    };
    public List<Room> beaconList(BeaconDto beaconDto) {
        return jpaRoomRepository.getByIdAndState(beaconDto.getId(),beaconDto.getState());
    }

    //얼츄완료
    public Map<Object, Object> around(BeaconDto beaconDto) {
        Map<Object, Object> beacon = new HashMap<>();

        for (int i = 0; i < beaconDto.getUuidList().size(); i++) {
            beacon.put(Integer.parseInt(String.valueOf(i)) + 1, jpaBeaconRepository.findUserByUuid(beaconDto.getUuidList().get(i)));
        }

        if(beacon.isEmpty()) {
            beacon.put("sc", 400);
        } else {
            beacon.put("sc", 200);
        }

        return beacon;
    }
    public Map joinBeacon(BeaconDto beaconDto){
        Room room = jpaRoomRepository.findByNumberAndId(beaconDto.getUuid(),beaconDto.getId());
        if(room == null){
            List<String> myList = new ArrayList<>();
            myList.add(beaconDto.getId());
            RoomDto roomDto = new RoomDto();
            roomDto.setNumber(beaconDto.getUuid());
            roomDto.setId(beaconDto.getId());
            roomDto.setIdList(myList);
            roomDto.setState(beaconDto.getState());

            Room newRoom = roomDto.toEntity(0);
            jpaRoomRepository.save(newRoom);
            res.put("sc","201");
            res.put("state",beaconDto.getState());
            res.put("number",beaconDto.getUuid());
        }
        else{
            System.out.println(room.toString());
            res.put("sc","200");
            res.put("state",beaconDto.getState());
            res.put("number",beaconDto.getUuid());
        }
        return res;
    };
    public Map deleteBeacon(BeaconDto beaconDto){
        res.put("sc","200");
        return res;
    };

    public Map updateBeacon(BeaconDto beaconDto){
        res.put("sc","200");
        return res;
    };


}
