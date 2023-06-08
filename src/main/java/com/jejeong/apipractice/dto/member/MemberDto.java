package com.jejeong.apipractice.dto.member;

import lombok.Getter;

@Getter
public class MemberDto {
    private Long id;

    private String email;

    private String password;

    private String nickname;
}
