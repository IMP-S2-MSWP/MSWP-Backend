package com.example.mswp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.util.Date;

@Table(name = "user")
@Entity // Class 위 선언하여 해당 Class가 Entity임을 알려줌, JPA에서 정의된 Field를 바탕으로 DB에 Table 생성
@Builder // 해당 Class에 해당하는 Entity 객체를 만들 때, Builder Pattern을 이용하여 만들 수 있게 지정
@AllArgsConstructor // 선언된 모든 Field를 Parameter로 갖는 생성자 자동 생성
@NoArgsConstructor // Parameter가 없는 Basic 생성자 자동 생성
@Getter // 각 Field의 Value를 조회할 수 있는 Getter 자동 생성
@Setter
@ToString // 해당 Class에 선언된 Field를 모두 출력할 수 있는 toString() 자동 생성
public class User {

    @Id // Entity의 PK가 될 Value 지정, 만약 @GeneratedValue 사용 시 auto_increment 사용 가능
    @Column(length = 20, nullable = false) // VARCHAR 길이(String Type에서만 사용 가능), null 허용 여부
    @NotBlank // null, "", " " 허용 X
    private String id;

    @Column(length = 60, nullable = false)
    @JsonIgnore
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 12, nullable = false)
    private String nickname;

    @Column(columnDefinition = "DATE")
    private Date birth;

    @Column
    private Character gender;

    @Column(length = 50)
    private String uuid;

    @Column(length = 30)
    private String image;

    @Column(length = 60)
    private String message;

}
