package com.apple.shop.domain.member.service;

import com.apple.shop.domain.member.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// 스프링 시큐리티에서 자동으로 호출

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepo memberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var opt = memberRepo.findFirstByLoginId(username);
        List<GrantedAuthority> auth= new ArrayList<>();
        if(opt.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        auth.add(new SimpleGrantedAuthority("일반유저"));


        var a=new CustomUser(opt.get().getLoginId(), opt.get().getLoginPw(), auth);
        a.displayName="1";
        a.id=opt.get().getId();
        return a;

    }

    public static class CustomUser extends User{
        public String displayName;
        public Long id;
        public CustomUser(
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities
        ){
            super(username,password,authorities);
        }
    }

}
