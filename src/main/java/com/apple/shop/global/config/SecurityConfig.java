package com.apple.shop.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //필터 정의들
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable());

        // CORS 허용
        http.cors(Customizer.withDefaults());

        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);

        // 세션데이터 생성 하지 말아주세요.
        http.sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    );

        http.authorizeHttpRequests((authorize) ->
        authorize
            .requestMatchers(
                "/css/**",
                "/js/**",
                "/images/**",
                "/webjars/**",
                "/favicon.ico"
            ).permitAll()
            .anyRequest().permitAll()
);
        http.formLogin((formLogin) -> formLogin.loginPage("/member/login")
//                        .loginProcessingUrl("/member/check") // 로그인 처리 요청 (POST)// 1. 로그인 할 URL
                        .defaultSuccessUrl("/item/list", true) // 2. 로그인 성공시 이동할 URL
//                .failureUrl("/fail") // 3. 로그인 실패시 이동할 URL
        );


        http.logout(logout-> logout.logoutUrl("/member/logout")
                .logoutSuccessUrl("/item/list")
        );

        return http.build();
    }



}
