package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.entity.member.Member;
import com.jejeong.apipractice.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService {

  private final MemberRepository memberRepository;

  @Override
  public MemberDto loadUserByUserEmail(String email) {
    return memberRepository.findByEmail(email).map(MemberDto::fromEntity)
        .orElseThrow(IllegalArgumentException::new);
  }

  @Override
  @Transactional
  public void deleteUserByUserEmail(String email) {
    Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
    member.hideWithdrawalInfo();
  }
}
