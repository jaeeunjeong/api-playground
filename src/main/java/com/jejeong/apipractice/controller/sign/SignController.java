package com.jejeong.apipractice.controller.sign;

import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.sevice.sign.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class SignController {

    private final SignService memberService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Valid> signUpRequest(@Valid @RequestBody SignUpRequest req) {
        memberService.signUp(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
