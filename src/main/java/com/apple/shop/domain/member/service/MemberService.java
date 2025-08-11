package com.apple.shop.domain.member.service;

import com.apple.shop.domain.member.entity.Member;
import com.apple.shop.domain.member.repo.MemberRepo;
import com.apple.shop.domain.member.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

     private final MemberRepo memberRepo;
     private final PasswordEncoder passwordEncoder;
    private final MemberValidator memberValidator = new MemberValidator();

    public boolean SavaMember( String loginId  , String loginPw,  String usrName, String Email,Model model) {

        Member member = new Member();

        if (!memberValidator.validateInput(loginId, loginPw, usrName, Email)) {
            model.addAttribute("error", "아이디 혹은 비밀번호는 20자 이하여야 합니다.");
            return false;
        }
        else if(IsExistLoginId(loginId)){
            model.addAttribute("error", "이미 존재하는 ID입니다.");
            return false;
        }
        else {
            member.setLoginId(loginId);
            member.setLoginPw(passwordEncoder.encode(loginPw));
            member.setUsrName(usrName);
            member.setEmail(Email);
            memberRepo.save(member);
            return true;
        }
    }

    public boolean IsExistLoginId(String loginId ){
        if(memberRepo.existsByLoginId(loginId)){
            return true;
        }
        return false;

    }

    public Optional<Member> findFirstByLoginId(String loginId){
        return memberRepo.findFirstByLoginId(loginId);
    }

    public Optional<Member> findById(Long id) {
        return memberRepo.findById(id);
}



}
