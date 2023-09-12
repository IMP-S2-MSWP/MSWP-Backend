package com.example.mswp.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity // Class 위 선언하여 해당 Class가 Entity임을 알려줌, JPA에서 정의된 Field를 바탕으로 DB에 Table 생성
@Builder // 해당 Class에 해당하는 Entity 객체를 만들 때, Builder Pattern을 이용하여 만들 수 있게 지정
@AllArgsConstructor // 선언된 모든 Field를 Parameter로 갖는 생성자 자동 생성
@NoArgsConstructor // Parameter가 없는 Basic 생성자 자동 생성
@Getter // 각 Field의 Value를 조회할 수 있는 Getter 자동 생성
@ToString // 해당 Class에 선언된 Field를 모두 출력할 수 있는 toString() 자동 생성
public class User {

    @Id //기본키를 의미. 반드시 기본키를 가져야함.
    @Column(name = "id", length = 64)
    private String id;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 12, nullable = false)
    private String nickname;

    /*@Builder
    public User(String id,String password, String nickname) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
    }*/


}
