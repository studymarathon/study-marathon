package com.github.studym.studymarathon.config.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Setter
@Getter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String email;
    private String password;
    private String nickname;
    private boolean fromSocial;
    private boolean del;
    private Map<String, Object> attr;
    
    public AuthMemberDTO(String username,
                         String password,
                         String nickname,
                         boolean fromSocial,
                         boolean del,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.nickname = nickname;
        this.del = del;
        this.fromSocial = fromSocial;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }


    @Override
    public String getName() {
        return this.email;
    }
}
