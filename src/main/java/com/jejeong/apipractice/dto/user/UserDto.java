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
public class UserDto implements UserDetails {

  private Long id;
  private String email;
  private String password;
  private String username;
  private UserRole userRole;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private Timestamp removedAt;


  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(userRole.toString()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return removedAt == null;
  }

  @Override
  public boolean isAccountNonLocked() {
    return removedAt == null;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return removedAt == null;
  }

  @Override
  public boolean isEnabled() {
    return removedAt == null;
  }

  public static UserDto fromEntity(User user) {
    return new UserDto(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getUserName(),
        user.getRole(),
        user.getCreatedAt(),
        user.getUpdatedAt(),
        user.getRemovedAt()
    );
  }
}
