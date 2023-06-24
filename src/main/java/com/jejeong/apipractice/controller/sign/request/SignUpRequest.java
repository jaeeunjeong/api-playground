package com.jejeong.apipractice.controller.sign.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotNull(message = "이메일은 필수 값입니다.")
    @Email(message = "잘못된 이메일 양식입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 값입니다.")
    private String password;

    @NotNull(message = "닉네임은 필수 값입니다.")
    @Size(min = 3, max = 10)
    private String nickname;

    public static SignUpRequest of(String email, String password, String nickname) {
        SignUpRequest req = new SignUpRequest();

        req.email = email;
        req.password = password;
        req.nickname = nickname;

        return req;
    }
}
