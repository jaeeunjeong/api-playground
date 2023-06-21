package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.controller.sign.request.SignInRequest;
import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.controller.sign.response.SignInResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.entity.member.Member;
import com.jejeong.apipractice.exception.exception.ApplicationException;
import com.jejeong.apipractice.repository.member.MemberRepository;
import com.jejeong.apipractice.sevice.sign.GeneralMemberSignService;
import com.jejeong.apipractice.util.JwtTokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.jejeong.apipractice.fixture.MemberFixture.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneralMemberSignServiceTest {

    @InjectMocks
    private GeneralMemberSignService memberService;
    @Mock
    private MemberRepository memberRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void beforeEach() {
        memberService = new GeneralMemberSignService(memberRepository);
    }

    @Test
    void signUp() {
        // given
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");

        // when
        memberService.signUp(req);

        // then
        verify(memberRepository, times(1)).saveAndFlush(ArgumentMatchers.any());
    }

    @Test
    void validateSignUpInfo() {
        // given
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");
        given(memberRepository.existsByEmail(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> memberService.signUp(req)).isInstanceOf(ApplicationException.class);
    }

    @DisplayName("nickname duplicate")
    @Test
    void test2() {
        // given
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");
        given(memberRepository.existsByNickname(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> memberService.signUp(req)).isInstanceOf(ApplicationException.class);
    }

    @Test
    void loadUserByUserEmail() {
        // given
        Member member = createMember();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        // when
        MemberDto memberDto = memberService.findMember(member.getEmail());

        // then
        assertThat(memberDto.getEmail()).isEqualTo(member.getEmail());

    }

    @Test
    @DisplayName("회원이 없는 경우 에러를 내뱉는다.")
    void test3() {
        // given
//        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // then
//        assertThatThrownBy(() -> memberService.findMember(anyString())).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteUserByUserEmail() {
        // given
        Member member = createMember();
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        // when
        memberService.deleteUserByUserEmail(member.getEmail());

        // then
        assertThat(member.getEmail()).isEqualTo("******");
    }

    @Test
    @DisplayName("로그인 테스트")
    void test4() {
        // given
        String email = "email@email.com";
        String password = "password";
        SignInRequest req = new SignInRequest();
        req.setEmail(email);
        req.setPassword(password);
        String encodedPassword = passwordEncoder.encode(password);

//        UserDetails savedMember = User.builder().username(email).password(encodedPassword).build();
//        given(memberService.loadUserByUserEmail(email)).willReturn(savedMember);
//        given(passwordEncoder.matches(password, savedMember.getPassword())).willReturn(true);
//        given(JwtTokenUtils.generateAccessToken()).willReturn("access-token");
//        given(JwtTokenUtils.generateRefreshToken()).willReturn("refresh-token");

        // when
        SignInResponse res = memberService.signIn(req);

        // then
        assertThat(res).isNotNull();
        assertThat(res.getAccessToken()).isEqualTo("access-token");
        assertThat(res.getRefreshToken()).isEqualTo("refresh-token");

    }

}