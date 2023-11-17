package com.example.mswp.dto;

import com.example.mswp.entity.Beacon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BeaconDto {

    private String uuid;
    private String id;
    private Character state;
    private String message;
    private String image;
    private String beaconname;

    private List<String> uuidList;
    @Builder
    public Beacon toEntity() {
        return Beacon.builder()
                .uuid(uuid)
                .id(id)
                .state(state)
                .message(message)
                .image(image)
                .beaconname(beaconname)
                .create_at(LocalDateTime.now())
                .build();
    }

}