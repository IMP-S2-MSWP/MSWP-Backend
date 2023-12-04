package com.example.mswp.dto;

import com.example.mswp.entity.Beacon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeaconDto {

    private String uuid;
    private String creator;
    private Character state;
    private String message;
    private String image;
    private String title;
    private String advertisementImage;
    private String beaconname;
    private Character gender;

    private List<String> uuidList;
    private String id;

    public Beacon toEntity() {
        return Beacon.builder()
                .uuid(uuid)
                .creator(creator)
                .state(state)
                .message(message)
                .image(image)
                .title(title)
                .advertisementImage(advertisementImage)
                .beaconname(beaconname)
                .gender(gender)
                .create_at(LocalDateTime.now())
                .update_at(LocalDateTime.now())
                .build();
    }

}