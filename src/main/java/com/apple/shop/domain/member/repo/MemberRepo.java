package com.apple.shop.domain.member.repo;

import com.apple.shop.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member,Long> {

    // Derived query method
    public boolean existsByLoginId(String loginId);
    public Optional<Member> findFirstByLoginId(String loginId);
}
