package com.example.mswp.controller;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.mapping.UserMapping;
import com.example.mswp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "Success!";
    }

    @PostMapping("/login")
    public Optional<User> login(@RequestBody UserDto userDto) {
        return userService.findByIdAndPassword(userDto);
    }

    @PostMapping("/info")
    public Optional<UserMapping> info(@RequestBody UserDto userDto) {
        return userService.loadUser(userDto);
    }

}
