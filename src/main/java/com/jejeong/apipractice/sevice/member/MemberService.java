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
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto loadUserByUserEmail(String email) {
        return memberRepository.findByEmail(email).map(MemberDto::fromEntity).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void deleteUserByUserEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        member.hideWithdrawalInfo();
    }
}
