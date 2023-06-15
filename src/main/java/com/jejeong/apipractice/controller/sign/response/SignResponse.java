package com.jejeong.apipractice.controller.sign.response;

import lombok.Getter;

@Getter
public class SignResponse {
    private String email;
    private String nickname;

    public static SignResponse of(String email, String nickname) {
        SignResponse res = new SignResponse();

        res.email = email;
        res.nickname = nickname;

        return res;
    }
}
