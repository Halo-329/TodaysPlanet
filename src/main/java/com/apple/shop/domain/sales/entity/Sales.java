package com.apple.shop.domain.sales.entity;

import com.apple.shop.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Sales {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String itemName;
    Integer price;
    Integer count;
//    Long memberId;

    @ManyToOne(fetch = FetchType.LAZY) // member에 접근할 때만 Member DB 조회
    @JoinColumn(
            name="member_id",
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private Member member;

    @CreationTimestamp
    LocalDateTime created;

}
