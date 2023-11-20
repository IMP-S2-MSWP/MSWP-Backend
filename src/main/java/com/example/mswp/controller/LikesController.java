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
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LikesController {

    @Autowired
    private LikesService likesService;


    @PostMapping("/click")
    public Optional<Likes> clickLike(@RequestBody LikesDto likesDto) {
        return likesService.clickLike(likesDto);
    }

    @GetMapping("/count")
    public Map<String, List<Object>> countLike(@RequestParam String id) {
        return likesService.countLike(id);
    }

    @PostMapping("/test")
    public Set<String> test(@RequestBody UserDto userDto) {
        return likesService.test(userDto);
    }

    @GetMapping("/list")
    public Map<Object, Object> likeMe(@RequestParam String id) {
        return likesService.likeMe(id);
    }
}
