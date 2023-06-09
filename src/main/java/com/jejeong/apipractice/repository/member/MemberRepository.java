package com.jejeong.apipractice.repository.member;

import com.jejeong.apipractice.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByNickname(String nickname);
}
