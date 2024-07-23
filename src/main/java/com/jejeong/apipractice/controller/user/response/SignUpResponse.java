package com.jejeong.apipractice.controller.user.response;

import com.jejeong.apipractice.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {

  private Long id;
  private String name;

  public static SignUpResponse fromUserDto(UserDto user) {
    return new SignUpResponse(user.getId(), user.getUsername());
  }
}
