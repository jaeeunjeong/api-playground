package com.jejeong.apipractice.controller.user.request;

import lombok.Data;

@Data
public class SignUpRequest {

  private String email;
  private String password;
  private String username;
}
