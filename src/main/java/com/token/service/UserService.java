package com.token.service;

import com.token.dto.MemberDto;
import com.token.entity.Member;
import com.token.entity.MemberRole;
import com.token.repository.MemberRepository;
import com.token.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signup(MemberDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        List<MemberRole> role = new ArrayList<>();
        role.add(MemberRole.USER);

        Member user = Member.builder()
                .uid(0L)
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .activated(true)
                .roles(role)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMember(String username) {
        return userRepository.findByEmail(username);
    }

}
