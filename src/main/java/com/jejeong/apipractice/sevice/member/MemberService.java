package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.entity.member.Member;

public interface MemberService {
    public void registrationMember(Member member);

    public boolean isDuplicateEmail(String email);

    public boolean isDuplicateNickname(String nickname);

}
