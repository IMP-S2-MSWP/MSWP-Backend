package com.example.mswp.controller;

import com.example.mswp.dto.BeaconDto;
import com.example.mswp.entity.Beacon;
import com.example.mswp.entity.Room;
import com.example.mswp.service.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/beacon")
@CrossOrigin(origins = "*")
public class BeaconController {

    @Autowired
    private BeaconService beaconService;

    @PostMapping("/create")
    public Map createBeacon(@RequestBody BeaconDto beaconDto){
        return beaconService.createBeacon(beaconDto);
    }

    @PostMapping("/mybeacon")
    public List<Beacon> myBeaconList (@RequestBody BeaconDto beaconDto){
        return beaconService.myBeaconList(beaconDto);
    }

    @PostMapping("/beaconlist")
    public List<Room> beaconList (@RequestBody BeaconDto beaconDto){
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
}