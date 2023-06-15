package com.jejeong.apipractice.sevice.sign;

import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
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
public class GeneralMemberSignService implements SignService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        // TODO password encoding
        Member member = Member.of(req.getEmail(), req.getPassword(), req.getNickname());
        memberRepository.saveAndFlush(member);
    }

    @Override
    public void validateSignUpInfo(SignUpRequest req) {
        if (memberRepository.existsByEmail(req.getEmail())) throw new IllegalArgumentException();
        if (memberRepository.existsByNickname(req.getNickname())) throw new IllegalArgumentException();
    }

}
