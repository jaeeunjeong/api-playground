package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.controller.member.request.SignUpRequest;
import com.jejeong.apipractice.dto.member.MemberDto;

public interface MemberService {
    public void signUp(SignUpRequest req);

    public void validateSignUpInfo(SignUpRequest req);

    public MemberDto loadUserByUserEmail(String email);

    public void deleteUserByUserEmail(String email);
}
