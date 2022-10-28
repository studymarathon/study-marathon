package com.github.studym.studymarathon.domain.member.service;

import com.github.studym.studymarathon.domain.member.dto.MemberJoinDTO;

public interface MemberService {

    void join(MemberJoinDTO memberJoinDTO) throws EmailExistException;

    static class EmailExistException extends Exception {

    }
}
