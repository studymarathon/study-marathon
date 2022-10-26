package com.github.studym.studymarathon.config.security.service;

import com.github.studym.studymarathon.config.security.dto.AuthMemberDTO;
import com.github.studym.studymarathon.domain.member.entity.Member;
import com.github.studym.studymarathon.domain.member.entity.MemberRole;
import com.github.studym.studymarathon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class OAuthUserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("--------------------------------------");
        log.info("userRequest:" + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("=====================================");
        oAuth2User.getAttributes().forEach((k, v) -> log.info(k + ":" + v));

        String email = oAuth2User.getAttribute("email");


        log.info("EMAIL: " + email);

        Member member = saveSocialMember(email);

        AuthMemberDTO dto =
                new AuthMemberDTO(
                        member.getEmail(),
                        member.getPassword(),
                        member.getNickname(),
                        member.isFromSocial(),
                        member.getRoleSet()
                                .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name()))
                                .collect(Collectors.toList())
        );

        return dto;
    }

    private Member saveSocialMember(String email) {
        // 기존에 동일한 이메일로 가입한 회원은 그대로 조회만
        return repository.findByEmail(email, true)
                .orElseGet(() -> getNewMember(email));
    }

    private Member getNewMember(String email) {
        // 없다면 회원 추가 소셜 아이디라서 패스워드를 사용할일이 없음 그러니 임의로 1111지정 이메일은 연동한 이메일
        Member newMember = Member.builder()
                .email(email)
                .nickname(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        newMember.addMemberRole(MemberRole.USER);
        repository.save(newMember);

        return newMember;
    }
}
