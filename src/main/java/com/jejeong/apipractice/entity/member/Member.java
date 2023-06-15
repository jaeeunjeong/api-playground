package com.jejeong.apipractice.entity.member;

import com.jejeong.apipractice.entity.common.EntityDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    public static Member of(String email, String password, String nickname) {
        Member inst = new Member();
        inst.email = email;
        inst.password = password;
        inst.nickname = nickname;
        return inst;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void hideWithdrawalInfo() {
        this.email = "******";
        this.nickname = "******";
    }
}
