package com.example.mswp.controller;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.service.SignUpService;



import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SignUpController {
    // protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SignUpService signUpService;

    @PostMapping ("/register")
    public Map SignUpMethod(@RequestBody UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        String result = this.signUpService.SignUpMethod(userDto);
        response.put("sc",result);

        return response;
    }

    @PostMapping ("/test")
    public Map testMethod (@RequestBody UserDto userDto){
        Map<String, Object> result = new HashMap<>();
        result.put("test1", "test1");
        result.put("test2", "test2");
        return result;

    }
}
