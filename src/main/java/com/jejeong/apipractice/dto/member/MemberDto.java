package com.jejeong.apipractice.dto.member;

import com.jejeong.apipractice.entity.member.Member;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class MemberDto {

    private Long id;

    private String email;

    private String nickname;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    private Timestamp removeAt;

    protected MemberDto() {
    }

    public MemberDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberDto fromEntity(Member entity) {
        MemberDto dto = new MemberDto();

        dto.id = entity.getId();
        dto.email = entity.getEmail();
        dto.nickname = entity.getNickname();
        dto.registeredAt = entity.getRegisteredAt();
        dto.updatedAt = entity.getUpdatedAt();
        dto.removeAt = entity.getRemovedAt();

        return dto;
    }
}
