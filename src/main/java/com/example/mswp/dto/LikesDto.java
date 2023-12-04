package com.example.mswp.dto;

import com.example.mswp.entity.Likes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDto {

    private int number;
    private String idFrom;
    private String idTo;
    private LocalDateTime expiredAt;

    public Likes toEntity() {
        return Likes.builder()
                .number(number)
                .idFrom(idFrom)
                .idTo(idTo)
                .build();
    }
}
