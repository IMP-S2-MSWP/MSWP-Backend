package com.example.mswp.controller;

import com.example.mswp.dto.BeaconDto;
import com.example.mswp.entity.Beacon;
import com.example.mswp.service.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/beacon")
@CrossOrigin(origins = "*")
public class BeaconController {

    @Autowired
    private BeaconService beaconService;

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Map createBeacon(@RequestParam("uuid") String uuid,
                            @RequestParam("creator") String creator,
                            @RequestParam("state") Character state,
                            @RequestParam("message") String message,
                            @RequestParam("beaconname") String beaconname,
                            @RequestParam("gender") Character gender,
                            @RequestParam("file") MultipartFile file
                            ) throws IOException {
        return beaconService.createBeacon(uuid, creator, state, message, beaconname, gender, file);
    }

    @PostMapping("/mybeacon")
    public List<Beacon> myBeaconList (@RequestBody BeaconDto beaconDto){
        return beaconService.myBeaconList(beaconDto);
    }

    @PostMapping("/list")
    public List<Beacon> beaconList (@RequestBody BeaconDto beaconDto){
        return beaconService.beaconList(beaconDto);
    }

    @PostMapping("/around")
    public Map<Object, Object> around (@RequestBody BeaconDto beaconDto){
        return beaconService.around(beaconDto);
    }

    @PostMapping("/delete")
    public Map deleteBeacon(@RequestBody BeaconDto beaconDto){
        return beaconService.deleteBeacon(beaconDto);
    }

    @PostMapping("/join")
    public Map joinBeacon(@RequestBody BeaconDto beaconDto){
        return beaconService.joinBeacon(beaconDto);
    };

    @PostMapping("/update")
    public Map updateBeacon(@RequestBody BeaconDto beaconDto){
        return beaconService.updateBeacon(beaconDto);
    };

    @PostMapping(value = "/advertisement", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Map<String, Object> createAdvertisement(@RequestParam("uuid") String uuid,
                                                   @RequestParam("title") String title,
                                                   @RequestParam("file") MultipartFile file
    ) throws IOException {
        return beaconService.createAdvertisement(uuid, title, file);
    }

    @PostMapping("/show")
    public Optional<Beacon> showAdvertisement(@RequestBody BeaconDto beaconDto) {
        return beaconService.showAdvertisement(beaconDto);
    }

//    @PostMapping("upload")
//    public Map uploadImage(@RequestParam("uuid") String uuid, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
//        return beaconService.uploadImage(uuid, file);
//    }

}