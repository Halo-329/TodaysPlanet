package com.apple.shop.domain.sales.repo;


import com.apple.shop.domain.sales.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalesRepo extends JpaRepository< Sales, Long>{

    @Query(value = "select s from Sales s join fetch s.member")
    List<Sales> findAllWithJPQL();
    List<Sales> findAllByMember_LoginId(String loginId);


}

