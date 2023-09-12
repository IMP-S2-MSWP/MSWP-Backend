package com.example.mswp.controller;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "엉덩이 흔들어!";
    }

    @PostMapping("/login")
    public Optional<User> login(@RequestBody UserDto userDto) {
        System.out.println(userDto.getId());
        System.out.println(userDto.getPassword());
        return userService.findByIdAndPassword(userDto);

    }

}
