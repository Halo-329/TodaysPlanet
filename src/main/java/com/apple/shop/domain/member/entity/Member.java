package com.apple.shop.domain.member.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column
    private String loginId;
    private String loginPw;
    private String UsrName;
    private String Email;


}
