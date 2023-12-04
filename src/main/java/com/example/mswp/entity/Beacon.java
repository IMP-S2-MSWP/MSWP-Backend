package com.example.mswp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Beacon {

    @Id
    @Column(length = 50, nullable = false)
    private String uuid;

    @Column(length = 50)
    private String creator;

    @Column
    private Character state;

    @Column(length = 60)
    private String message;

    @Column
    private String image;

    @Column
    private String title;

    @Column(name = "advertisement_image")
    private String advertisementImage;

    @Column(length = 60)
    private String beaconname;

    @Column
    private Character gender;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime create_at;

    @Column(name = "update_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime update_at;

    @Column(name = "expired_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime expired_at;

    public void updateAdvertisementImage(String title, String advertisementImage) {
        this.title = title;
        this.advertisementImage = advertisementImage;
    }
}