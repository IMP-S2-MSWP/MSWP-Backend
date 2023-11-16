package com.example.mswp.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Beacon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int sequence;

    @Column(length = 50)
    private String uuid;

    @Column(length = 50)
    private String id;

    @Column()
    private Character state;
    @Column(length = 60)
    private String message;

    @Column(length = 255)
    private String image;

    @Column(length = 60)
    private String beaconname;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime create_at;

    @Column(name = "expried_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime expried_at;
}