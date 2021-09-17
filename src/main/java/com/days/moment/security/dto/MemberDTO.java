package com.days.moment.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.days.moment.security.domain.Member;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberDTO extends User {

    private String mid;
    private String mpw;
    private String mname;
    private boolean enalbed;

    public MemberDTO(Member member){
        super(member.getMid(),
                member.getMpw(),
                member.getRoleList().stream().map(memberRole ->
                        new SimpleGrantedAuthority(memberRole.getRole())).collect(Collectors.toList()));

        this.mid = member.getMid();
        this.mpw = member.getMpw();
        this.mname = member.getMname();
        this.enalbed = member.isEnabled();
    }

    public MemberDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
