package com.example.mswp.controller;

import com.example.mswp.dto.LikesDto;
import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.Likes;
import com.example.mswp.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/like")
@CrossOrigin(origins = "*")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @PostMapping("/click")
    public Optional<Likes> click(@RequestBody LikesDto likesDto) {
        return likesService.click(likesDto);
    }

    @GetMapping("/count")
    public Map<String, List<Likes>> count(@RequestParam String id) {
        return likesService.count(id);
    }

    @PostMapping("/me")
    public Set<String> me(@RequestBody UserDto userDto) {
        return likesService.me(userDto);
    }

    @GetMapping("/list")
    public Map<Object, Object> list(@RequestParam String id) {
        return likesService.list(id);
    }
}
