package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.entity.member.Member;
import com.jejeong.apipractice.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void registrationMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean isDuplicateNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
