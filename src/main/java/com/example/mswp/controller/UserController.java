package com.example.mswp.controller;


import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public Optional<User> login(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }

    @PostMapping("/register/validation")
    public Map<String, Integer> idValidation(@RequestBody UserDto userDto) {
        return userService.idValidation(userDto);
    }

    @PostMapping("/main")
    public Optional<User> home(@RequestBody UserDto userDto) {
        return userService.home(userDto);
    }

    @PostMapping("/around")
    public Map<Object, Object> around(@RequestBody UserDto userDto) {
        return userService.around(userDto);
    }

    @PostMapping ("/register")
    public Map register(@RequestBody UserDto userDto) {
        Map<String, Object> map = new HashMap<>();

        String result = userService.register(userDto);
        map.put("sc",result);

        return map;
    }

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Map uploadImage(@RequestParam("id") String id,
                           @RequestParam(value = "file") MultipartFile file
                           ) throws IOException {
        return userService.uploadImage(id, file);
    }

    @PostMapping("/message")
    public Map<String, Integer> changeMessage(@RequestBody UserDto userDto) {
        return userService.changeMessage(userDto);
    }

}

