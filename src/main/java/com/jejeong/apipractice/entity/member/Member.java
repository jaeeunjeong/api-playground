package com.jejeong.apipractice.entity.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 30)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "nickname", unique = true, nullable = false, length = 30)
    private String nickname;

    protected Member() {
    }

    public static Member of(String email, String password, String nickname) {
        Member inst = new Member();
        inst.email = email;
        inst.password = password;
        inst.nickname = nickname;
        return inst;
    }
}
