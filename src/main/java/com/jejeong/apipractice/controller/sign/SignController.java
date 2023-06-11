package com.jejeong.apipractice.controller.sign;

import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.controller.sign.response.SignResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.sevice.member.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SignController {

    private final SignService memberService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpRequest(@Valid @RequestBody SignUpRequest req) {
        memberService.signUp(req);
    }

    @GetMapping("/me")
    public SignResponse processUserInfoRequest(String email) {
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        MemberDto member = memberService.loadUserByUserEmail(email);
        return SignResponse.of(member.getEmail(), member.getNickname());
    }

    @DeleteMapping("/me")
    public void processWithdrawalRequest(String email) {
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        memberService.deleteUserByUserEmail(email);
    }
}
