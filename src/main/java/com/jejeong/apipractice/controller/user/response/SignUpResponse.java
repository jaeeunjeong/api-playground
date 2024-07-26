package com.jejeong.apipractice.controller.user.response;

import com.jejeong.apipractice.dto.user.UserDto;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {

  private Long id;
  private String name;
  private Timestamp createAt;


  public static SignUpResponse fromUserDto(UserDto user) {
    return new SignUpResponse(user.getId(), user.getUsername(),
        user.getCreatedAt());
  }
}
