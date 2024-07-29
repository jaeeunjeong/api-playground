package com.jejeong.apipractice.service.user;

import com.jejeong.apipractice.controller.user.request.SignInRequest;
import com.jejeong.apipractice.controller.user.request.SignUpRequest;
import com.jejeong.apipractice.controller.user.response.SignInResponse;
import com.jejeong.apipractice.dto.user.UserDto;
import com.jejeong.apipractice.entity.user.User;
import com.jejeong.apipractice.exception.ApiPracticeApplicationException;
import com.jejeong.apipractice.exception.ErrorCode;
import com.jejeong.apipractice.repository.user.UserRepository;
import com.jejeong.apipractice.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService { // implements UserDetailsService

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtTokenUtils jwtTokenUtils;
//
//  @Override
//  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    return userRepository.findByEmail(email).map(UserDto::fromEntity).orElseThrow(
//        () -> new ApiPracticeApplicationException(ErrorCode.USER_NOT_FOUND,
//            String.format("user not founded.")));
//  }


  public UserDto signUp(SignUpRequest req) {

    userRepository.findByEmail(req.getEmail()).ifPresent((ip) -> {
      throw new ApiPracticeApplicationException(
          ErrorCode.DUPLICATED_EMAIL, String.format("email is %s", req.getEmail()));
    });
    String password = passwordEncoder.encode(req.getPassword());
    User user = userRepository.save(User.of(req.getEmail(), password, req.getUsername()));
    return UserDto.fromEntity(user);
  }

  public SignInResponse signIn(SignInRequest req) {
    UserDto userDto = loadUserByEmail(req.getEmail());
    if (!passwordEncoder.matches(req.getPassword(), userDto.getPassword())) {
      throw new ApiPracticeApplicationException(ErrorCode.INVALID_PASSWORD);
    }

    String accessToken = jwtTokenUtils.generateAccessToken(userDto.getEmail());
    String refreshToken = jwtTokenUtils.generateRefreshToken(userDto.getEmail());
    return new SignInResponse(userDto.getEmail(), accessToken, refreshToken);
  }

  public UserDto loadUserByEmail(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email).map(UserDto::fromEntity).orElseThrow(
        () -> new ApiPracticeApplicationException(ErrorCode.USER_NOT_FOUND,
            String.format("email is %s", email))
    );
  }
}
