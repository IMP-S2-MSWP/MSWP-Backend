package com.example.mswp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicUpdate
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int sequence;

    @Column(length = 45, nullable = false)
    private String number;

    @Column(length = 20, nullable = false)
    private String id;

    @Column
    private Character state;

    @Column(name = "create_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime create_at;

    @Column(name = "update_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime update_at;

    @Column(name = "expired_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime expired_at;

}
