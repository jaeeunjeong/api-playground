package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.entity.member.Member;
import com.jejeong.apipractice.repository.member.MemberRepository;
import com.jejeong.apipractice.sevice.sign.GeneralMemberSignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.jejeong.apipractice.fixture.MemberFixture.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneralMemberSignServiceTest {

    private GeneralMemberSignService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        memberService = new GeneralMemberSignService(memberRepository, passwordEncoder);
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
        assertThatThrownBy(() -> memberService.signUp(req)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("nickname duplicate")
    @Test
    void test2() {
        // given
        SignUpRequest req = SignUpRequest.of("email@example.com", "password", "nickname");
        given(memberRepository.existsByNickname(anyString())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> memberService.signUp(req)).isInstanceOf(IllegalArgumentException.class);
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
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> memberService.findMember(anyString())).isInstanceOf(IllegalArgumentException.class);
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
}