package com.jejeong.apipractice.fixture;

import com.jejeong.apipractice.entity.member.Member;

public class MemberFixture {
    public static Member createMember() {
        return Member.of("email@gmail.com", "password", "nickname");
    }
}
