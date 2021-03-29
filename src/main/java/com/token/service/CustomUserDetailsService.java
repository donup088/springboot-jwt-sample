package com.token.service;

import com.token.entity.Member;
import com.token.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        log.info("username: " + username);
        //UsernameNotFoundException 예외를 던지지만 authenticate 메소드 안에서  try catch로 잡고 BadCredentialsException 예외를 던지도록 되어있다.
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("이메일에 해당하는 계정이 없습니다."));
        return CustomUserDetails.create(member);
    }

}
