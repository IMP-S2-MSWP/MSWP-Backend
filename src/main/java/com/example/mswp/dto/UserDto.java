package com.example.mswp.dto;

import com.example.mswp.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserDto {
        
        private String id;
        private String password;
        private String name;
        private String nickname;
        private Date birth;
        private Character gender;
        private String uuid;
        private String image;
        private String message;
        private String fcm;

        //여러 아이디 받기 위함 (uuid 기반 사용자 닉네임 제공)
        private List<String> uuidList;

        public User toEntity() {
                //@AllArgsConstructor 이랑 builder 겹쳐서 에러남
                return User.builder()
                        .id(id)
                        .password(password)
                        .name(name)
                        .nickname(nickname)
                        .birth(birth)
                        .gender(gender)
                        .uuid(uuid)
                        .image(image)
                        .message(message)
                        .fcm(fcm)
                        .build();
        }   

}
