package com.github.studym.studymarathon.domain.member.service;

import com.github.studym.studymarathon.domain.member.dto.MemberJoinDTO;
import com.github.studym.studymarathon.domain.member.entity.Member;
import com.github.studym.studymarathon.domain.member.entity.MemberRole;
import com.github.studym.studymarathon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws EmailExistException {


        String email = memberJoinDTO.getEmail();

        boolean exist = memberRepository.existsById(email);

        if (exist) {
            throw new EmailExistException();
        }

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getPassword()));
        member.addMemberRole(MemberRole.USER);

        log.info("=================");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepository.save(member);
    }
}
