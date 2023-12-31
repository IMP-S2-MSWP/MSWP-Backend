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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BeaconService {

    @Autowired
    private final S3Service s3Service;
    @Autowired
    private final JpaBeaconRepository jpaBeaconRepository;
    @Autowired
    private final JpaRoomRepository jpaRoomRepository;

    Map<String, Object> res = new HashMap<>();

    //Beacon 등록
    public Map createBeacon(String uuid,
                            String creator,
                            Character state,
                            String message,
                            String beaconname,
                            Character gender,
                            MultipartFile file) throws IOException {

        Beacon beacon = jpaBeaconRepository.findUserByUuid(uuid);

        if (beacon == null) {
            // 기존 파일명 -> 사용자 ID로 변경하기 위함
            String originalFilename = file.getOriginalFilename().replace(file.getOriginalFilename(), uuid + ".jpg");
            s3Service.saveFile(uuid, "beacon", file);

            BeaconDto beaconDto = new BeaconDto();

            beaconDto.setUuid(uuid);
            beaconDto.setCreator(creator);
            beaconDto.setState(state);
            beaconDto.setMessage(message);
            beaconDto.setImage(originalFilename);
            beaconDto.setBeaconname(beaconname);
            beaconDto.setGender(gender);

            jpaBeaconRepository.save(beaconDto.toEntity());
            res.put("sc", 200);
        } else {
            res.put("sc", 400);
        }
        return res;
    }

    // 내가 등록한 Beacon
    public List<Beacon> myBeaconList (BeaconDto beaconDto) {
        return jpaBeaconRepository.findByCreator(beaconDto.getCreator());
    }

    //
    public List<Beacon> beaconList(BeaconDto beaconDto) {
        return jpaBeaconRepository.findBeaconsByRoomIdAndState(beaconDto.getId(),beaconDto.getState());;
    }
    
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

    // Beacon 참가
    public Map joinBeacon(BeaconDto beaconDto){
        Room room = jpaRoomRepository.findByNumberAndId(beaconDto.getUuid(),beaconDto.getId());
        if(room == null){
            List<String> myList = new ArrayList<>();
            myList.add(beaconDto.getId());
            RoomDto roomDto = new RoomDto();
            roomDto.setNumber(beaconDto.getUuid());
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

    // 비콘 광고 등록
    public Map<String, Object> createAdvertisement(String uuid, String title, MultipartFile file) throws IOException {

        Beacon beacon = jpaBeaconRepository.findUserByUuid(uuid);

        if (beacon != null) {
            // 기존 파일명 -> 사용자 ID로 변경하기 위함
            String originalFilename = file.getOriginalFilename().replace(file.getOriginalFilename(), uuid + ".jpg");
            s3Service.saveFile(uuid, "advertisement", file);

            beacon.updateAdvertisementImage(title, originalFilename);

            jpaBeaconRepository.save(beacon);
            res.put("sc", 200);
        } else {
            res.put("sc", 400);
        }
        return res;

    }

    // 비콘 광고 노출
    public Optional<Beacon> showAdvertisement(BeaconDto beaconDto) {
        return jpaBeaconRepository.findByUuid(beaconDto.getUuid());
    }

}
