package com.jejeong.apipractice.controller.member;

import com.jejeong.apipractice.controller.member.response.MemberResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.sevice.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping
    public ResponseEntity<MemberResponse> processMemberInfoRequest(@RequestParam String email) {
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        MemberDto member = memberService.loadUserByUserEmail(email);
        if (member != null)
            return ResponseEntity.ok(MemberResponse.of(member.getEmail(), member.getNickname()));
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> processWithdrawalRequest(@RequestParam String email) {
        // TODO email 대신 사용자 정보로 조회할 수 있게끔 해야함.
        if (email.isBlank()) return ResponseEntity.badRequest().build();

        memberService.deleteUserByUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
