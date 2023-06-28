package com.jejeong.apipractice.configuration.filter;

import static org.junit.jupiter.api.Assertions.*;

import com.jejeong.apipractice.repository.member.MemberRepository;
import com.jejeong.apipractice.sevice.sign.GeneralMemberSignService;
import com.jejeong.apipractice.sevice.sign.SignService;
import com.jejeong.apipractice.util.JwtTokenUtils;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
public class JwtTokenFilterTest {

  @MockBean
  private SignService signService;

  private JwtTokenFilter jwtTokenFilter;

  private MockHttpServletRequest request;
  private MockHttpServletResponse response;
  private MockFilterChain filterChain;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // 필요한 의존성을 주입하여 JwtTokenFilter 인스턴스 생성
    jwtTokenFilter = new JwtTokenFilter(signService, "your-secret-key");

    // Mock 객체로 HttpServletRequest, HttpServletResponse, FilterChain 생성
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    filterChain = new MockFilterChain();
  }

  @Test
  @DisplayName("정상적인 JWT 토큰을 검증하는 경우")
  void testValidToken() throws Exception {
    // given
    String token = "valid-jwt-token";
    String userEmail = "test@example.com";
    UserDetails userDetails = createUserDetails(userEmail);

    // loadUserByUserEmail() 메서드가 유효한 UserDetails를 반환하도록 설정
    Mockito.when(signService.loadUserByUserEmail(userEmail)).thenReturn(userDetails);

    // JwtTokenUtils.validate() 메서드가 true를 반환하도록 설정
    Mockito.when(JwtTokenUtils.validate(token, userEmail, "your-secret-key")).thenReturn(true);

    // "Bearer 토큰값" 형식의 Authorization 헤더 설정
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    // when
    jwtTokenFilter.doFilterInternal(request, response, filterChain);

    // then
    // 검증된 인증 정보가 SecurityContextHolder에 설정되었는지 확인
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Assertions.assertNotNull(authentication);
    Assertions.assertEquals(userDetails, authentication.getPrincipal());

    // FilterChain이 실행되었는지 확인
//    Assertions.assertTrue(filterChain.isFilterInvoked());
  }

  //  @Test
  @DisplayName("유효하지 않은 JWT 토큰을 검증하는 경우")
  void testInvalidToken() throws Exception {
    // given
    String token = "invalid-jwt-token";
    String userEmail = "test@example.com";
    UserDetails userDetails = createUserDetails(userEmail);

    // loadUserByUserEmail() 메서드가 유효한 UserDetails를 반환하도록 설정
    Mockito.when(signService.loadUserByUserEmail(userEmail)).thenReturn(userDetails);

    // JwtTokenUtils.validate() 메서드가 false를 반환하도록 설정
    Mockito.when(JwtTokenUtils.validate(token, userEmail, "your-secret-key")).thenReturn(false);

    // "Bearer 토큰값" 형식의 Authorization 헤더 설정
    request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    // when
    jwtTokenFilter.doFilterInternal(request, response, filterChain);

    // then
    // 인증 정보가 설정되지 않았는지 확인
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Assertions.assertNull(authentication);

    // FilterChain이 실행되었는지 확인
//    Assertions.assertTrue(filterChain.isFilterInvoked());
  }

  private UserDetails createUserDetails(String userEmail) {
    // 테스트에 필요한 UserDetails 객체를 생성하여 반환
    return User.builder()
        .username(userEmail)
        .password("password")
        .authorities(Collections.emptyList())
        .build();
  }
}
