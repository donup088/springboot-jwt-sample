package com.token.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MemberAdapter extends User {
    private Member member;

    public MemberAdapter(Member member) {
        super(member.getEmail(), member.getPassword(), authorities(member.getRoles()));
        this.member = member;
    }

    public MemberAdapter(Member member, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.member = member;
    }

    public static MemberAdapter fromAccountModel(Member member) {
        return new MemberAdapter(member, member.getEmail(), member.getPassword(), authorities(member.getRoles()));
    }

    private static Collection<? extends GrantedAuthority> authorities(List<MemberRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toList());
    }

    public Member getMember() {
        return member;
    }
}
