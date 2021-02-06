package com.token.service;

import com.token.entity.Member;
import com.token.entity.MemberAdapter;
import com.token.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        Member member = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));

        return new MemberAdapter(member);
    }
}
