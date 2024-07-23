package com.jejeong.apipractice.controller.user;

import com.jejeong.apipractice.controller.user.request.SignUpRequest;
import com.jejeong.apipractice.controller.user.response.SignUpResponse;
import com.jejeong.apipractice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/sign-up")
  public SignUpResponse signUp(@RequestBody SignUpRequest req) {
    return SignUpResponse.fromUserDto(userService.join(req));
  }
}
