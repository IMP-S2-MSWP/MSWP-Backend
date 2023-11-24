package com.example.mswp.service;

import com.example.mswp.dto.BeaconDto;
import com.example.mswp.entity.Room;
import com.example.mswp.entity.Beacon;
import com.example.mswp.repository.JpaBeaconRepository;
import com.example.mswp.dto.RoomDto;
import com.example.mswp.repository.JpaRoomRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BeaconService {

    private final S3Service s3Service;
    private final JpaBeaconRepository jpaBeaconRepository;
    private final JpaRoomRepository jpaRoomRepository;

    Map<String, Object> res = new HashMap<>();

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
    public List<Beacon> myBeaconList (BeaconDto beaconDto) {
        return jpaBeaconRepository.findByCreator(beaconDto.getCreator());
    }


    public List<Beacon> beaconList(BeaconDto beaconDto) {
        List<Beacon> test = jpaBeaconRepository.findBeaconsByRoomIdAndState(beaconDto.getCreator(),beaconDto.getState());
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
        Room room = jpaRoomRepository.findByNumberAndId(beaconDto.getUuid(),beaconDto.getCreator());
        if(room == null){
            List<String> myList = new ArrayList<>();
            myList.add(beaconDto.getCreator());
            RoomDto roomDto = new RoomDto();
            roomDto.setNumber(beaconDto.getUuid());
            roomDto.setId(beaconDto.getCreator());
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

    public Map uploadImage(String uuid, MultipartFile file) throws IOException {

        Map<String, Integer> res = new HashMap<>();

        // 기존 파일명 -> 사용자 ID로 변경하기 위함
        String originalFilename = file.getOriginalFilename().replace(file.getOriginalFilename(), uuid + ".jpg");

        Path filePath = Paths.get(".\\src\\main\\resources\\static\\images\\", originalFilename);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Optional<Beacon> beacon = jpaBeaconRepository.findByUuid(uuid);

        if (beacon.isPresent()) {
            beacon.get().setImage(originalFilename);
            jpaBeaconRepository.save(beacon.get());
            res.put("sc", 200);
        } else {
            res.put("sc", 400);
        }

        return res;

    }

}
