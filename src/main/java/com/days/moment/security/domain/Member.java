package com.days.moment.security.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private String mid;
    private String mpw;
    private String mname;
    private boolean enabled;

    private List<MemberRole> roleList;
}
