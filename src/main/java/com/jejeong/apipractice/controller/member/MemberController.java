package com.jejeong.apipractice.controller.member;

import com.jejeong.apipractice.controller.member.response.MemberResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.sevice.member.MemberService;
import com.jejeong.apipractice.sevice.sign.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final SignService signService;


  @GetMapping("/me")
  public ResponseEntity<MemberResponse> processUserInfoRequest(@AuthenticationPrincipal User user) {
    MemberDto member = signService.findMember(user.getUsername());

    if (member != null) {
      return ResponseEntity.ok(MemberResponse.of(member.getEmail(), member.getNickname()));
    }

    return ResponseEntity.notFound().build();
  }

}
