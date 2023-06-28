package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.entity.member.Member;
import com.jejeong.apipractice.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.jejeong.apipractice.fixture.MemberFixture.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

  private MemberService memberService;

  @Mock
  private MemberRepository memberRepository;

  @BeforeEach
  void beforeEach() {
    memberService = new GeneralMemberService(memberRepository);
  }

  @Test
  void loadUserByUserEmail() {
    // given
    Member member = createMember();
    given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

    // when
    MemberDto memberDto = memberService.loadUserByUserEmail(member.getEmail());

    // then
    assertThat(memberDto.getEmail()).isEqualTo(member.getEmail());

  }

  @Test
  @DisplayName("회원이 없는 경우 에러를 내뱉는다.")
  void test3() {
    // given
    given(memberRepository.findByEmail(anyString())).willReturn(Optional.empty());

    // then
    assertThatThrownBy(() -> memberService.loadUserByUserEmail(anyString())).isInstanceOf(
        IllegalArgumentException.class);
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