package com.jejeong.apipractice.controller.sign;

import com.jejeong.apipractice.controller.sign.request.SignInRequest;
import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.controller.sign.response.SignResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.sevice.sign.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> signUpRequest(@Valid @RequestBody SignUpRequest req) {
        signService.signUp(req);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<SignResponse> processUserInfoRequest(@AuthenticationPrincipal User user) {
        MemberDto member = signService.findMember(user.getUsername());

        if (member != null)
            return ResponseEntity.ok(SignResponse.of(member.getEmail(), member.getNickname()));

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> processWithdrawalRequest(@AuthenticationPrincipal User user) {
        String email = user.getUsername();

        if (email.isBlank()) return ResponseEntity.badRequest().build();
        signService.deleteUserByUserEmail(email);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> signInRequest(@Valid @RequestBody SignInRequest req) {
        signService.signIn(req);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}