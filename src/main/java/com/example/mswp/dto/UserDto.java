package com.example.mswp.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.mswp.entity.User;

@Getter
@Setter
public class UserDto {
        
        private String id;
        private String password;
        private String nickname;
        
        public User toEntity() {
                /* @AllArgsConstructor 이랑 builder가 겹쳐서 에러남
                User user = User.builder().id(this.id)
                        .password(this.password)
                        .nickname(this.nickname).build();
                */
                User user = new User(this.id,this.password,this.nickname);
                return user;
        }
        
        
}
