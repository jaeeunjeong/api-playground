package com.jejeong.apipractice.service.user;

import com.jejeong.apipractice.controller.user.request.SignUpRequest;
import com.jejeong.apipractice.dto.user.UserDto;
import com.jejeong.apipractice.entity.user.User;
import com.jejeong.apipractice.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserDto join(SignUpRequest req) {
    User user = userRepository.save(User.of(req.getEmail(), req.getPassword(), req.getUsername()));
    return UserDto.fromEntity(user);
  }
}
