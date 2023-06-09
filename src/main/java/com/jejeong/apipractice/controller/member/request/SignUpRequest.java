package com.jejeong.apipractice.controller.member.request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String nickname;

    public static SignUpRequest of(String email, String password, String nickname) {
        SignUpRequest req = new SignUpRequest();

        req.email = email;
        req.password = password;
        req.nickname = nickname;

        return req;
    }
}
