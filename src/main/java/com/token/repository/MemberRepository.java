package com.token.repository;

import com.token.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String username);

    Optional<Member> findByUidAndProvider(Long id, String provider);

}
