package com.token.controller;

import com.token.dto.KakaoProfile;
import com.token.dto.MemberDto;
import com.token.dto.TokenDto;
import com.token.entity.Member;
import com.token.entity.MemberRole;
import com.token.exception.CUserExistException;
import com.token.exception.CUserNotFoundException;
import com.token.jwt.JwtFilter;
import com.token.jwt.TokenProvider;
import com.token.repository.MemberRepository;
import com.token.service.CurrentUser;
import com.token.service.CustomUserDetails;
import com.token.service.KakaoService;
import com.token.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final UserService userService;
    private final KakaoService kakaoService;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(
            @Valid @RequestBody MemberDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo(@CurrentUser CustomUserDetails member) {
        log.info(""+member.getMember());
        return ResponseEntity.ok(userService.getCurrentMember().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getMember(username).get());
    }

    @PostMapping("/signin/{provider}")
    public ResponseEntity signinByProvider(@PathVariable String provider,
                                           @RequestParam String accessToken) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Member member = memberRepository.findByUidAndProvider(profile.getId(), provider)
                .orElseThrow(CUserNotFoundException::new);
        String jwt = tokenProvider.createToken(member.getEmail());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup/{provider}")
    public ResponseEntity signupProvider(@PathVariable("provider") String provider,
                                         @RequestParam("accessToken") String accessToken,
                                         @RequestParam("name") String name) {
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<Member> optionalMember = memberRepository.findByUidAndProvider(profile.getId(), provider);
        if(optionalMember.isPresent()){
            throw new CUserExistException();
        }
        memberRepository.save(Member.builder()
                .uid(profile.getId())
                .provider(provider)
                .nickname(name)
                .roles(List.of(MemberRole.USER))
                .build());
        return ResponseEntity.ok("성공하였습니다.");
    }
}
