package com.days.moment.security.mapper;

import com.days.moment.security.domain.Member;

public interface MemberMapper {

    public Member findByMid(String mid);
}
