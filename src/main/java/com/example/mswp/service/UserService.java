package com.example.mswp.service;

import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.User;
import com.example.mswp.repository.JpaBeaconRepository;
import com.example.mswp.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JpaUserRepository jpaUserRepository;
    private final JpaBeaconRepository jpaBeaconRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //ID, Password data로 사용자 데이터 추출
    public Optional<User> login(UserDto userDto) {

        //ID 값으로 user data 추출
        Optional<User> user = jpaUserRepository.findById(userDto.getId());

        //user data 내 암호화된 Password와 사용자가 입력한 Password를 디코딩하여 비교
        if (user.isPresent() && passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            return user;
        } else {
            return Optional.empty();
        }

    }

    //Password를 제외한 사용자 data 추출
    public Optional<User> main(UserDto userDto) {

        return jpaUserRepository.findById(userDto.getId());
    }

    //bluetooth id로 주변 사용자 nickname 조회
    public Map<Object, Object> around(UserDto userDto) {
        Map<Object, Object> user = new HashMap<>();

        for (int i = 0; i < userDto.getUuidList().size(); i++) {
            if (userDto.getUuidList().get(i).startsWith("bc2")) {
                user.put(Integer.parseInt(String.valueOf(i)) + 1, jpaUserRepository.findUserByUuid(userDto.getUuidList().get(i)));
            } else {
                user.put(Integer.parseInt(String.valueOf(i)) + 1, jpaBeaconRepository.findUserByUuid(userDto.getUuidList().get(i)));
            }
        }

        if(user.isEmpty()) {
            user.put("sc", 400);
        } else {
            user.put("sc", 200);
        }

        return user;
    }

    public String register(UserDto userDto) {

        StringBuilder uuid = new StringBuilder();

        //패스워드 암호화
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // 아이디 찾는 메소드
        Optional<User> userTest = jpaUserRepository.findById(userDto.getId());

        String response = "400";

        uuid.append(UUID.randomUUID());
        uuid.replace(0, 3, "bc2");


        //유저 id가 db에 있는지 확인
        if (userTest.isEmpty()) {
            User newUser = userDto.toEntity();
            newUser.setUuid(uuid.toString());
            newUser.setImage("no_image.jpg");
            jpaUserRepository.save(newUser);
            response = "200";
        }

        return response;
    }

    //아이디 중복 확인
    public Map<String, Integer> idValidation(UserDto userDto) {
        //상태 코드 res
        Map<String, Integer> res = new HashMap<>();
        // 중복 ID 찾기
        Optional<User> user = jpaUserRepository.findById(userDto.getId());

        res.put("sc", user.isPresent() ? 400 : 200);

        return res;
    }

    // 사용자 이미지 변경
    public Map uploadImage(String id, MultipartFile file) throws IOException {

        Map<String, Integer> res = new HashMap<>();

        // 기존 파일명 -> 사용자 ID로 변경하기 위함
        String originalFilename = file.getOriginalFilename().replace(file.getOriginalFilename(), id + ".jpg");

        Path filePath = Paths.get(".\\src\\main\\resources\\static\\images\\", originalFilename);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Optional<User> user = jpaUserRepository.findById(id);

        if (user.isPresent()) {
            user.get().setImage(originalFilename);
            jpaUserRepository.save(user.get());
            res.put("sc", 200);
        } else {
            res.put("sc", 400);
        }

        return res;
    }

}