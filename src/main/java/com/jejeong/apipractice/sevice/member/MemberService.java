package com.jejeong.apipractice.sevice.member;

import com.jejeong.apipractice.dto.member.MemberDto;

public interface MemberService {

  public MemberDto loadUserByUserEmail(String email);

  public void deleteUserByUserEmail(String email);

}
