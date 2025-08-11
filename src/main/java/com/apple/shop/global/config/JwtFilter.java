package com.apple.shop.global.config;

import com.apple.shop.domain.member.service.MyUserDetailsService.CustomUser;
import com.apple.shop.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 쿠키 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 쿠키 찾기
        Cookie jwtCookie = null;
        for (Cookie c : cookies) {
            if ("jwt".equals(c.getName())) {
                jwtCookie = c;
                break;
            }
        }

        // jwt 쿠키가 없다면 다음 필터로 진행
        if (jwtCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // JWT 검증
        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie.getValue());
        } catch (Exception e) {
            System.out.println("(Error) " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // 사용자 권한 세팅
        String[] arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr)
                .map(SimpleGrantedAuthority::new)
                .toList();

        String username = claim.get("username").toString();
        CustomUser customUser = new CustomUser(username, "", authorities);
        customUser.displayName = claim.get("displayName").toString();

        // Spring Security 인증 객체 설정
        var authToken = new UsernamePasswordAuthenticationToken(
                customUser, "", authorities
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터 실행
        filterChain.doFilter(request, response);
    }
}
