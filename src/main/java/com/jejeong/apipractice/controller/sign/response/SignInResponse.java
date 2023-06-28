package com.jejeong.apipractice.controller.sign.response;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class SignInResponse {
    private String accessToken;
    private String refreshToken;

    protected SignInResponse() {
    }

    public static SignInResponse of(String accessToken, String refreshToken) {
        SignInResponse res = new SignInResponse();

        res.accessToken = accessToken;
        res.refreshToken = refreshToken;

        return res;
    }
}
