package com.jejeong.apipractice.controller.user.request;

import lombok.Data;

@Data
public class SignInRequest {

  private String email;
  private String password;
}
