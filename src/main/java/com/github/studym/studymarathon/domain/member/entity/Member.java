package com.github.studym.studymarathon.domain.member.entity;

import com.github.studym.studymarathon.domain.Util.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private boolean fromSocial;

    private boolean del;

    private LocalDateTime delDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeSocial(boolean fromSocial) {
        this.fromSocial = fromSocial;
    }


}
