package com.jejeong.apipractice.controller.sign;

import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.controller.sign.response.SignResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.sevice.member.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SignController {

    private final SignService memberService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Valid> signUpRequest(@Valid @RequestBody SignUpRequest req) {
        memberService.signUp(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<SignResponse> processUserInfoRequest(@RequestParam String email) {
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        MemberDto member = memberService.loadUserByUserEmail(email);
        if (member != null)
            return ResponseEntity.ok(SignResponse.of(member.getEmail(), member.getNickname()));
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> processWithdrawalRequest(@RequestParam String email) {
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        if (email.isBlank()) return ResponseEntity.badRequest().build();

        memberService.deleteUserByUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
