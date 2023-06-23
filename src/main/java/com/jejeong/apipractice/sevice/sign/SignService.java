package com.jejeong.apipractice.sevice.sign;

import com.jejeong.apipractice.controller.sign.request.SignInRequest;
import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.controller.sign.response.SignInResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface SignService {
    void signUp(SignUpRequest req);

    void validateSignUpInfo(SignUpRequest req);

    MemberDto findMember(String email);

    void deleteUserByUserEmail(String email);

    SignInResponse signIn(SignInRequest req);

    UserDetails loadUserByUserEmail(String userEmail);
}
