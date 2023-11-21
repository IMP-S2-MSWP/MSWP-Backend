package com.example.mswp.service;

import com.example.mswp.dto.BeaconDto;
import com.example.mswp.entity.Room;
import com.example.mswp.entity.Beacon;
import com.example.mswp.repository.JpaBeaconRepository;
import com.example.mswp.dto.RoomDto;
import com.example.mswp.repository.JpaRoomRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BeaconService {

    @Autowired
    private final JpaBeaconRepository jpaBeaconRepository;
    @Autowired
    private final JpaRoomRepository jpaRoomRepository;
    Map<String, Object> res = new HashMap<>();
    public Map createBeacon(BeaconDto beaconDto){
        System.out.println(beaconDto.getUuid());
        Beacon beacon = jpaBeaconRepository.findUserByUuid(beaconDto.getUuid());

        if(beacon == null){
            beaconDto.setImage("no_beacon_image.jpg");
            beacon = beaconDto.toEntity();
            jpaBeaconRepository.save(beacon);
            res.put("sc","200");
        }
        //이미 해당 uuid가 등록되어 있음
        else {
            res.put("sc","400");
        }
        return res;
    }

    //완
    public List<Beacon> myBeaconList (BeaconDto beaconDto){
        return jpaBeaconRepository.getById(beaconDto.getId());
    }

    public List<Beacon> beaconList(BeaconDto beaconDto) {
        List<Beacon> test = jpaBeaconRepository.findBeaconsByRoomIdAndState(beaconDto.getId(),beaconDto.getState());
        return test;
        //return jpaRoomRepository.getByIdAndState(beaconDto.getId(),beaconDto.getState());
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
            res.put("sc","200");
            res.put("state",beaconDto.getState());
            res.put("number",beaconDto.getUuid());
        }
        return res;
    }

    //아직 안만듬
    public Map deleteBeacon(BeaconDto beaconDto){
        res.put("sc","200");
        return res;
    }

    public Map updateBeacon(BeaconDto beaconDto){
        Beacon test = jpaBeaconRepository.findUserByUuid(beaconDto.getUuid());
        if (test == null){

            res.put("sc","400");
        }
        else {
            jpaBeaconRepository.save(beaconDto.toEntity());
            res.put("sc","200");
        }

        return res;
    }

}
