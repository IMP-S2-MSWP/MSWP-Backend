package com.example.mswp.controller;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.mapping.UserMapping;
import com.example.mswp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
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

    @PostMapping("/my-page")
    public Optional<User> myPage(@RequestBody UserDto userDto) {
        return userService.myPage(userDto);
    }

    @PostMapping("/around")
    public Map aroundUser(@RequestBody UserDto userDto) {
        return userService.findUserByUuid(userDto);
    }

    @PostMapping ("/register")
    public Map SignUpMethod(HttpServletRequest request, @RequestBody UserDto userDto) {
        System.out.println(request.getRemoteAddr());

        Map<String, Object> responsedata = new HashMap<>();

        String result = this.userService.SignUpMethod(userDto);
        responsedata.put("sc",result);

        return responsedata;
    }

//    @PostMapping("/bluetooth")
//    public Map<String, Integer> insertBluetooth(@RequestBody UserDto userDto) {
//        return userService.insertUuid(userDto);
//    }

    @PostMapping("/upload")
    public Map uploadImage(@RequestParam("id") String id, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return userService.uploadImage(id, file);
    }


}
