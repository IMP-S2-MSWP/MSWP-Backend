package com.example.mswp.dto;

import com.example.mswp.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDto {

    private int sequence;
    private int number;
    private String id;

    //상태 코드
    private int sc;
    //여러 아이디 받기 위함
    private List<String> idList;

    @Builder
    public Room toEntity(int i) {
        return Room.builder()
                .id(idList.get(i))
                .number(number)
                .build();
    }

}
