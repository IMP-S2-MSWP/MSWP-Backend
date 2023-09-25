package com.example.mswp.controller;

import com.example.mswp.dto.RoomDto;
import com.example.mswp.entity.Room;
import com.example.mswp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/room")
    public List<Room> roomList(@RequestBody RoomDto roomDto) {
        return roomService.loadRoom(roomDto);
    }

    @PostMapping("/create")
    public Map createRoom(@RequestBody RoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }
}
