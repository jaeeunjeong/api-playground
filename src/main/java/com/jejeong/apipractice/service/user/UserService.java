package com.jejeong.apipractice.service.user;

import com.jejeong.apipractice.controller.user.request.SignUpRequest;
import com.jejeong.apipractice.dto.user.UserDto;
import com.jejeong.apipractice.entity.user.User;
import com.jejeong.apipractice.exception.ApiPracticeApplicationException;
import com.jejeong.apipractice.exception.ErrorCode;
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

    userRepository.findByEmail(req.getEmail()).ifPresent((ip) -> {
      throw new ApiPracticeApplicationException(
          ErrorCode.DUPLICATED_EMAIL, String.format("email is %s", req.getEmail()));
    });

    User user = userRepository.save(User.of(req.getEmail(), req.getPassword(), req.getUsername()));
    return UserDto.fromEntity(user);
  }
}
