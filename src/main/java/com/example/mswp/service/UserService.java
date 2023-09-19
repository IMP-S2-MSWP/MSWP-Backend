package com.example.mswp.service;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.mapping.UserMapping;
import com.example.mswp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    //ID, Password data로 사용자 데이터 추출
    public Optional<User> findByIdAndPassword(UserDto userDto) {

        //ID 값으로 user data 추출
        Optional<User> user = userRepository.findById(userDto.getId());
        
        //user data 내 암호화된 Password와 사용자가 입력한 Password를 디코딩하여 비교
        if(user.isPresent() && passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            return user;
        } else {
            return Optional.empty();
        }

    }

    //Password를 제외한 사용자 data 추출
    public Optional<UserMapping> loadUser(UserDto userDto) {
        return userRepository.findAllById(userDto.getId());
    }

    public String SignUpMethod(UserDto userDto){
        //패스워드 암호화
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // 아이디 찾는 메소드
        Optional<User> userTest = userRepository.findById(userDto.getId());

        String response = "400";

        //유저 id가 db에 있는지 확인
        if (userTest.isEmpty()){
            User newUser = userDto.toEntity();
            userRepository.save(newUser);
            response = "200";
        }
        return response;
    };

}
