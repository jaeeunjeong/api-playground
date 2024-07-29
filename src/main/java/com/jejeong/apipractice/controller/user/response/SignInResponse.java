package com.jejeong.apipractice.controller.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponse {

  private String email;
  private String accessToken;
  private String refreshToken;

}
