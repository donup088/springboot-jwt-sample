package com.token.provider;

import com.token.entity.Member;
import com.token.entity.MemberAdapter;
import com.token.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        log.info("username: " + username);
        log.info("password: " + password);

        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new NoSuchElementException("입력하신 이메일의 해당하는 계정이 없습니다."));

        if (isCorrectPassword(password, member)) {
            return PostAuthorizationToken.getTokenFromAccountContext(MemberAdapter.fromAccountModel(member));
        }

        throw new NoSuchElementException("비밀번호가 잘못되었습니다.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }

    private boolean isCorrectPassword(String password, Member member) {
        return passwordEncoder.matches(password, member.getPassword());
    }
}
