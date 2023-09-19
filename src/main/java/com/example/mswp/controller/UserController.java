package com.example.mswp.controller;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.mapping.UserMapping;
import com.example.mswp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
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

    @PostMapping ("/register")
    public Map SignUpMethod(HttpServletRequest request, @RequestBody UserDto userDto) {
        System.out.println(request.getRemoteAddr());

        Map<String, Object> responsedata = new HashMap<>();

        String result = this.userService.SignUpMethod(userDto);
        responsedata.put("sc",result);

        return responsedata;
    }

}
