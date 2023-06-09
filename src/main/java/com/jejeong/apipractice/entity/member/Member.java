package com.jejeong.apipractice.entity.member;

import com.jejeong.apipractice.entity.common.EntityDate;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SQLDelete(sql = "UPDATE \"member\" SET removed_at = NOW() WHERE id = ?")
public class Member extends EntityDate {

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

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void hideWithdrawalInfo() {
        this.email = "******";
        this.nickname = "******";
    }
}
