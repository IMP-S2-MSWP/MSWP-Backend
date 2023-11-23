package com.example.mswp.dto;

import com.example.mswp.entity.Beacon;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BeaconDto {

    private String uuid;
    private String creator;
    private Character state;
    private String message;
    private String image;
    private String beaconname;
    private Character gender;

    private List<String> uuidList;

    public Beacon toEntity() {
        return Beacon.builder()
                .uuid(uuid)
                .creator(creator)
                .state(state)
                .message(message)
                .image(image)
                .beaconname(beaconname)
                .gender(gender)
                .create_at(LocalDateTime.now())
                .build();
    }

}