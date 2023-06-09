package com.jejeong.apipractice.controller.member.response;

import lombok.Getter;

@Getter
public class UserResponse {
    private String email;
    private String nickname;

    public static UserResponse of(String email, String nickname) {
        UserResponse res = new UserResponse();

        res.email = email;
        res.nickname = nickname;

        return res;
    }
}
