package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.repository.member.MemberRepository;
import com.jejeong.apipractice.sevice.sign.GeneralMemberSignService;
import com.jejeong.apipractice.sevice.sign.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneralMemberSignServiceTest {

    private SignService signService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        signService = new GeneralMemberSignService(memberRepository);
    }

    @Test
    void signUp() {
        // given
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");

        // when
        signService.signUp(req);

        // then
        verify(memberRepository, times(1)).saveAndFlush(ArgumentMatchers.any());
    }

    @Test
    void validateSignUpInfo() {
        // given
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");
        given(memberRepository.existsByEmail(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> signService.signUp(req)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("nickname duplicate")
    @Test
    void test2() {
        // given
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");
        given(memberRepository.existsByNickname(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> signService.signUp(req)).isInstanceOf(IllegalArgumentException.class);
    }

}