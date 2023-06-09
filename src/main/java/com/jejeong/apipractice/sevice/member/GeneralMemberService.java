package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.controller.member.request.SignUpRequest;
import com.jejeong.apipractice.dto.member.MemberDto;
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
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        // TODO password encoding
        memberRepository.saveAndFlush(Member.of(req.getEmail(), req.getPassword(), req.getNickname()));
    }

    @Override
    public void validateSignUpInfo(SignUpRequest req) {
        if (memberRepository.existsByEmail(req.getEmail())) throw new RuntimeException();
        if (memberRepository.existsByNickname(req.getNickname())) throw new RuntimeException();
    }

    @Override
    public MemberDto loadUserByUserEmail(String email) {
        return memberRepository.findByEmail(email).map(MemberDto::fromEntity).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public void deleteUserByUserEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        member.hideWithdrawalInfo();
        memberRepository.delete(member);
    }
}
