package com.example.mswp.service;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String SignUpMethod(UserDto userDto){
        //패스워드 암호화
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // 아이디 찾는 메소드
        Optional<User> userTest = userRepository.findById(userDto.getId());

        String response = "400";

        //유저 id가 db에 있는지 확인
        if (userTest.isEmpty()) {
            User newUser = userDto.toEntity();
            userRepository.save(newUser);
            response = "200";
        }
        return response;
    }

}
