package com.example.mswp.dto;

import com.example.mswp.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoomListDto {

    private String number;
    private String id;
    private String rname;

    public RoomListDto(String number,String id, String rname){
        this.number=number;
        this.id = id;
        this.rname = rname;
    }



}
