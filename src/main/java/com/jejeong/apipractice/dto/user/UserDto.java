package com.jejeong.apipractice.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jejeong.apipractice.entity.user.User;
import com.jejeong.apipractice.enums.UserRole;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

  private Long id;
  private String username;
  private String password;
  private UserRole userRole;

  public static UserDto fromEntity(User user) {
    return new UserDto(
        user.getId(),
        user.getUserName(),
        user.getPassword(),
        user.getRole()
    );
  }
}
