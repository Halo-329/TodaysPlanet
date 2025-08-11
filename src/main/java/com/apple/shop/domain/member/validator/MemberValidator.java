package com.apple.shop.domain.member.validator;

import org.springframework.ui.Model;

public class MemberValidator
{
    public boolean validateInput( String loginId , String loginPw, String usrName, String email) {
        boolean IsValid = true;

        if (email.length() >= 2000 || loginId.length() >= 20 || loginPw.length() >= 20 ||usrName.length() >=20) {
            IsValid = false;
        }
        return IsValid;
    }

}
