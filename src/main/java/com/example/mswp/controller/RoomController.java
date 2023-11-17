package com.example.mswp.controller;

import com.example.mswp.dto.RoomDto;
import com.example.mswp.entity.Room;
import com.example.mswp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/room")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/list")
    public List<Room> roomList(@RequestBody RoomDto roomDto) {
        return roomService.roomList(roomDto);
    }

    @PostMapping("/create")
    public Map createRoom(@RequestBody RoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }
}