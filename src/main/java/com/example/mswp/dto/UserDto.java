package com.example.mswp.dto;

import com.example.mswp.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String id;
    private String password;
    private String nickname;

}
