package com.example.mswp.service;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.mapping.UserMapping;
import com.example.mswp.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    //bluetooth id로 주변 사용자 nickname 조회
    public Map findUserByBluetooth(UserDto userDto) {
        Map<String, Object> user = new HashMap<>();

        for (int i = 0; i < userDto.getBluetoothList().size(); i++) {
            user.put(String.valueOf(i), userRepository.findUserByBluetooth(userDto.getBluetoothList().get(i)));
        }

        return user;
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
    }

    public Map<String, Integer> insertBluetooth(UserDto userDto) {

        //상태 코드 res
        Map<String, Integer> res = new HashMap<>();
        //user 찾기
        Optional<User> user = userRepository.findById(userDto.getId());
        // 상태 코드 변수
        int sc = user.isPresent() ? 200 : 400;

        if (user.isPresent()) {
            user.get().setBluetooth(userDto.getBluetooth());
            userRepository.save(user.get());
            res.put("sc", sc);
        } else {
            res.put("sc", sc);
        }

        return res;
    }

    public Map<String, Integer> loginValidation(UserDto userDto) {
        //상태 코드 res
        Map<String, Integer> res = new HashMap<>();
        // 중복 ID 찾기
        Optional<User> user = userRepository.findById(userDto.getId());
        // 상태 코드 변수
        int sc = user.isPresent() ? 400 : 200;

        if(user.isPresent()) {
            res.put("sc", sc);
        } else {
            res.put("sc", sc);
        }

        return res;
    }

}
