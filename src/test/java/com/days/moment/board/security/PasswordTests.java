package com.days.moment.board.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.days.moment.common.config.RootConfig;
import com.days.moment.security.config.SecurityConfig;
import com.days.moment.security.domain.Member;
import com.days.moment.security.mapper.MemberMapper;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {SecurityConfig.class, RootConfig.class})
public class PasswordTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    MemberMapper memberMapper;

    @Test
    public void testMember() {
        String mid = "admin0";

        Member member = memberMapper.findByMid(mid);

        log.warn("------------------------------------");
        log.warn(member);

    }

    @Test
    public void testEncode() {
        String str = "member1";
        String enStr = passwordEncoder.encode(str);
        log.warn(enStr);
    }

    @Test
    public void testDecode() {
        String str = "$2a$10$xTFFWIDBh8PXAqR3vlagmODlN/rPxXDkZo67MPDtXAMRQUAPMVFPK";

        boolean match = passwordEncoder.matches("member1", str);

        log.warn(match);
    }

    @Test
    public void insertMember() {

        //insert into tbl_member (mid, mpw, mname) values ('mid', 'mpw', 'mname');
        //values값 replace
        String query = "insert into tbl_member (mid, mpw, mname) values ('v1', 'v2', 'v3');";

        for(int i = 0; i < 10; i++) {

            String mid = "user"+i; //user0
            String mpw = passwordEncoder.encode("pw"+i); //pw0 -> Bcrypted
            String mname = "유저"+i; //유저0

            String result = query;

            result = result.replace("v1", mid);
            result = result.replace("v2", mpw);
            result = result.replace("v3", mname);

            System.out.println(result);

        }
    }

    @Test
    public void insertAdmin() {

        //insert into tbl_member (mid, mpw, mname) values ('mid', 'mpw', 'mname');
        //values값 replace
        String query = "insert into tbl_member (mid, mpw, mname) values ('v1', 'v2', 'v3');";

        for(int i = 0; i < 10; i++) {

            String mid = "admin"+i; //user0
            String mpw = passwordEncoder.encode("pw"+i); //pw0 -> Bcrypted
            String mname = "관리자"+i; //유저0

            String result = query;

            result = result.replace("v1", mid);
            result = result.replace("v2", mpw);
            result = result.replace("v3", mname);

            System.out.println(result);

        }
    }

    @Test
    public void insertMemberRole() {

        String sql = "insert into tbl_member_role (mid, role) values ('%s', '%s');";

        for(int i = 0; i < 10; i ++) {

            String result = String.format(sql, "user" + i, "ROLE_MEMBER");

            System.out.println(result);

        }//end for
    }

    @Test
    public void insertAdminRole() {

        String sql = "insert into tbl_member_role (mid, role) values ('%s', '%s');";

        for(int i = 0; i < 10; i ++) {

            String result = String.format(sql, "admin" + i, "ROLE_MEMBER");

            System.out.println(result);

            String result2 = String.format(sql, "admin" + i, "ROLE_ADMIN");

            System.out.println(result2);

        }//end for
    }
}
