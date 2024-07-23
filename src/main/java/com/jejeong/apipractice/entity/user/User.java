package com.jejeong.apipractice.entity.user;

import com.jejeong.apipractice.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET removed_at = NOW() and is_delete = true where id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id = null;

  @Column(name = "email", unique = true)
  private String email;

  private String password;

  @Column(name = "user_name")
  private String userName;

  @Enumerated(EnumType.STRING)
  private UserRole role = UserRole.USER;

  public static User of(String email, String encodedPassword, String userName) {
    User user = new User();
    user.email = email;
    user.password = encodedPassword;
    user.userName = userName;
    return user;
  }
}
