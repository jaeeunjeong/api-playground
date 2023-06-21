package com.jejeong.apipractice.repository.member;

import com.jejeong.apipractice.entity.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.jejeong.apipractice.fixture.MemberFixture.createMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    @DisplayName("정상적인 회원 가입과 회원 조회")
    @Test
    void test1() {
        // given
        Member member = createMember();

        // when
        memberRepository.saveAndFlush(member);
        Member found = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);

        // then
        assertThat(found.getId()).isEqualTo(member.getId());

    }

    @DisplayName("날짜 확인 - common entity")
    @Test
    void test2() {
        // given
        Member member = createMember();

        // when
        memberRepository.saveAndFlush(member);

        // then
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);
        assertThat(foundMember.getRegisteredAt()).isNotNull();
        assertThat(foundMember.getRemovedAt()).isNotNull();
        assertThat(foundMember.getUpdatedAt()).isNotNull();
        System.out.println(foundMember.getRegisteredAt());
        System.out.println(foundMember.getRemovedAt());
        System.out.println(foundMember.getUpdatedAt());

    }

    @DisplayName("회원 정보 수정 확인")
    @Test
    void test3() {
        // given
        Member member = createMember();
        memberRepository.saveAndFlush(member);

        // when
        String newNickname = "nickname";
        member.updateNickname(newNickname);
        memberRepository.saveAndFlush(member);

        // then
        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);
        assertThat(foundMember.getNickname()).isEqualTo(newNickname);
    }

    @Test
    void existsByEmail() {
        // given
        Member member = createMember();
        memberRepository.saveAndFlush(member);

        memberRepository.findByEmail(member.getEmail());

        // when, then
        assertThat(memberRepository.existsByNickname(member.getEmail()));
        assertThat(memberRepository.existsByEmail("email")).isFalse();

    }

    @Test
    void existsByNickname() {
        // given
        Member member = createMember();
        memberRepository.saveAndFlush(member);

        // when, then
        assertThat(memberRepository.existsByNickname(member.getNickname())).isTrue();
        assertThat(memberRepository.existsByNickname("nick")).isFalse();

    }


}
