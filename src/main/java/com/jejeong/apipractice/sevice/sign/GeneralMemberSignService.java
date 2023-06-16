package com.jejeong.apipractice.sevice.sign;

import com.jejeong.apipractice.controller.sign.request.SignInRequest;
import com.jejeong.apipractice.controller.sign.request.SignUpRequest;
import com.jejeong.apipractice.controller.sign.response.SignInResponse;
import com.jejeong.apipractice.dto.member.MemberDto;
import com.jejeong.apipractice.entity.member.Member;
import com.jejeong.apipractice.exception.errorCode.CommonErrorCode;
import com.jejeong.apipractice.exception.errorCode.ErrorCode;
import com.jejeong.apipractice.exception.exception.ApplicationException;
import com.jejeong.apipractice.repository.member.MemberRepository;
import com.jejeong.apipractice.util.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneralMemberSignService implements SignService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        Member member = Member.of(req.getEmail(), passwordEncoder.encode(req.getPassword()), req.getNickname());
        memberRepository.saveAndFlush(member);
    }

    @Override
    public void validateSignUpInfo(SignUpRequest req) {
        if (memberRepository.existsByEmail(req.getEmail()))
            throw new ApplicationException(
                CommonErrorCode.DUPLICATED_USER_EMAIL, String.format("UserEmail is %s", req.getEmail()));
        if (memberRepository.existsByNickname(req.getNickname()))
            throw new ApplicationException(
                CommonErrorCode.DUPLICATED_USER_NICKNAME, String.format("UserNickname is %s", req.getNickname()));
    }

    @Override
    @Transactional
    public void deleteUserByUserEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        member.hideWithdrawalInfo();
    }

    @Override
    public MemberDto findMember(String email) {
        return null;
    }

    @Override
    public SignInResponse signIn(SignInRequest req) {
        return null;
    }
}
