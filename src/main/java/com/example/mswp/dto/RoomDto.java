package com.example.mswp.dto;

import com.example.mswp.entity.Room;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private int sequence;
    private String number;
    private String id;
    private Character state;
    //여러 아이디 받기 위함
    private List<String> idList;

    private String rname;


    @Builder
    public Room toEntity(int i) {
        return Room.builder()
                .id(idList.get(i))
                .number(number)
                .state(state)
                .create_at(LocalDateTime.now())
                .update_at(LocalDateTime.now())
                .build();
    }

}
