package com.jejeong.apipractice.controller.member.response;

import lombok.Getter;

@Getter
public class MemberResponse {
    private String email;
    private String nickname;

    public static MemberResponse of(String email, String nickname) {
        MemberResponse res = new MemberResponse();

        res.email = email;
        res.nickname = nickname;

        return res;
    }
}
