package com.github.studym.studymarathon.domain.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberJoinDTO {

    private String email;

    private String password;

    private String nickname;

    private boolean fromSocial;

    private boolean del;

    private LocalDateTime delDate;
}
