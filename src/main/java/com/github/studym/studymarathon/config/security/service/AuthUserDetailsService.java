package com.github.studym.studymarathon.config.security.service;

import com.github.studym.studymarathon.config.security.dto.AuthMemberDTO;
import com.github.studym.studymarathon.domain.member.entity.Member;
import com.github.studym.studymarathon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("----------------------------");
        log.info("AuthUserDetailsService loadUserByUsername" + username);

        Member member = checkMember(username);
        log.info("------------------------------------------");
        log.info(member);
        log.info("냥냥펀치확인");

        AuthMemberDTO dto = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                false,
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList())
        );

        log.info("AuthMemberDTO");
        log.info(dto);

        return dto;
    }

    private Member checkMember(String username) {

        return repository.getWithRoles(username).orElseThrow(() -> new UsernameNotFoundException("아이디가 없습니다."));
    }


}
