package com.apple.shop.global.util;

import com.apple.shop.domain.member.service.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    // 1. 키발급
    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
            ));


    // 2. JWT 생성
    public static String createToken(Authentication auth) {
        MyUserDetailsService.CustomUser usr = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        String authorities = auth.getAuthorities().stream()                 //getAuthorities -> List<auth객체> return
                .map(a->a.getAuthority())   // getAuthority() -> String return
                .collect(Collectors.joining(","));



        String jwt = Jwts.builder()
                .claim("username", usr.getUsername())
                .claim("displayName", usr.displayName )
                .claim("authorities", authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))  // expiration : 만료
                .signWith(key)
                .compact();
        return jwt;
    }


    //3. JWT 오픈
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }
}
