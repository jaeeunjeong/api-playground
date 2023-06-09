package com.jejeong.apipractice.controller.member;

import com.jejeong.apipractice.controller.member.request.SignUpRequest;
import com.jejeong.apipractice.controller.member.response.UserResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.sevice.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpRequest(@RequestBody SignUpRequest req) {
        memberService.signUp(req);
    }

    @GetMapping("/me")
    public UserResponse processUserInfoRequest(String email) {
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        MemberDto member = memberService.loadUserByUserEmail(email);
        return UserResponse.of(member.getEmail(), member.getNickname());
    }

    @DeleteMapping("/me")
    public void processWithdrawalRequest(String email){
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        memberService.deleteUserByUserEmail(email);
    }
}
