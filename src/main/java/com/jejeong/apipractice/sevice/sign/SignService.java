package com.jejeong.apipractice.sevice.sign;

import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.dto.member.MemberDto;

public interface SignService {
    public void signUp(SignUpRequest req);

    public void validateSignUpInfo(SignUpRequest req);

    public MemberDto loadUserByUserEmail(String email);

    public void deleteUserByUserEmail(String email);
}
