package com.apple.shop.domain.member.controller;

import com.apple.shop.domain.member.entity.Member;
import com.apple.shop.domain.member.repo.MemberRepo;
import com.apple.shop.domain.member.service.MemberService;
import com.apple.shop.domain.member.service.MyUserDetailsService;
import com.apple.shop.global.util.JwtUtil;
import com.apple.shop.view.ViewPath;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepo memberRepo;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 1. 회원가입
    @GetMapping("/signup")
    String signup() {
        return ViewPath.MEMBER_SIGNUP;
    }

    @PostMapping("/add")
    String add(String usrID, String password, String usrName, String email, Model model) {
        boolean result = memberService.SavaMember(usrID, password, usrName, email, model);

        if (!result) {
            return ViewPath.MEMBER_SIGNUP; // 실패 시 다시 입력페이지로
        }
        return ViewPath.REDIRECT_ITEM_LIST; // 성공 시 리스트로
    }


    // 2. 로그인 및 로그아웃

    // 2.1 로그인

    //2.1.1 세션 로그인
    @GetMapping("/login")
    String loginSession(String username, String password) {
        return ViewPath.MEMBER_LOGIN;
    }

    // 2.1.2 JWT 로그인 (HTML 리턴)
    @PostMapping("/login/jwt")
    public String loginJWT(@RequestParam String username,
                           @RequestParam String password,
                           HttpServletResponse response,
                           Model model) {

        var authToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

            var cookie = new Cookie("jwt", jwt);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            // 로그인 성공 후 페이지 이동
            return "redirect:/item/list";

        } catch (Exception e) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "feature/member/login"; // 로그인 실패 시 다시 로그인 페이지로
        }
    }




    // 2.2 세션 로그아웃, Spring Security가 해준다.
    @PostMapping("/logout")
    String logout(String username, String password) {
        return ViewPath.REDIRECT_ITEM_LIST;
    }

    // 2.3 jwt 로그아웃, 쿠키 삭제하는 방향으로
    @PostMapping("/logout/jwt")
    String jwtLogout(HttpServletResponse response){

        // 쿠키 덮어쓰기 방법으로 삭제
        // 유효 시간을 0초로 하고 HttpServletResponse 객체에 던져준다.
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ViewPath.REDIRECT_ITEM_LIST;
    }



    // 3. 마이페이지
    @GetMapping("/my-page")
    String me(Authentication auth, Model model) {
        String id = auth.getName();
        Optional<Member> opt = memberRepo.findFirstByLoginId(id);
        MyUserDetailsService.CustomUser result =  (MyUserDetailsService.CustomUser) auth.getPrincipal();

        if (opt.isPresent()) {
            model.addAttribute("member", opt.get());
            return ViewPath.MEMBER_MY_PAGE;
        }

        return "member/login";
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    String meByJwt(Authentication auth){

        if(auth == null){
            return "(Error) auth is null";
        }
        else{
            return auth.getPrincipal().toString();
        }
    }

    @GetMapping("/register")
    String register(Authentication auth) {
        if (auth==null || auth.isAuthenticated()) {
            return ViewPath.REDIRECT_ITEM_LIST;
        }
        return ViewPath.MEMBER_MY_PAGE;
    }


    @GetMapping("/usr/{id}")
    @ResponseBody
     MemberDTO GetUsr(@PathVariable Long id){
        var result =memberRepo.findById(id);
        MemberDTO memberDTO = new MemberDTO(result.get().getId(),result.get().getLoginId(), result.get().getUsrName());

    return memberDTO;
    }

    @RequiredArgsConstructor
    class MemberDTO{
        @NonNull
        public Long id;

        @NonNull
        public String usrName;

        @NonNull
        public String displayName;
    }



}

