package com.apple.shop.domain.sales.service;

import com.apple.shop.domain.item.entity.Item;
import com.apple.shop.domain.item.service.ItemService;
import com.apple.shop.domain.member.service.MyUserDetailsService;
import com.apple.shop.domain.sales.entity.Sales;
import com.apple.shop.domain.sales.repo.SalesRepo;
import com.apple.shop.domain.member.entity.Member;
import com.apple.shop.domain.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepo salesRepo;
    private final ItemService itemService;
    private final MemberService memberService;




    // 1. 저장
    @Transactional
    public void createSalesRecord(Long itemId, int count, Authentication auth) {
        Item item = itemService.FindItem(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Item이 존재하지 않습니다."));

        reduceItemStock(itemId, count);

        Sales sales = new Sales();
        sales.setItemName(item.getTitle());
        sales.setPrice(item.getPrice());
        sales.setCount(count);

        MyUserDetailsService.CustomUser usr = (MyUserDetailsService.CustomUser) auth.getPrincipal();
        Long memberId = memberService.findFirstByLoginId(usr.getUsername()).get().getId();
        Member member = new Member();
        member.setId(memberId);
        sales.setMember(member);

        salesRepo.save(sales);
    }

    //1.1 재고 감소
    public void reduceItemStock(Long itemId, int count) {
        if (!isStockAvailable(itemId, count)) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        itemService.reduceStock(itemId, count);
    }


    // 2. DB 행 불러오기
    public List<Sales> getAllOrderDetailList(){

        return salesRepo.findAll();
    }

    // 2.1 DB 행 불러오기 (원하는 ID 만)
    public List<Sales> getOrderDetailListByLoginId(String loginId) {
        return salesRepo.findAllByMember_LoginId(loginId);
    }

    // 3. 재고 체크
    public boolean isStockAvailable(Long itemId, int count){
        Item item=null;


        Optional<Item> opt = itemService.FindItem(itemId);

        if(opt.isPresent()){
            item=opt.get();
        }

        if(item.getStock()-count<0){
            return false;
        }


        return true;
    }


}
@Transactional
@RequiredArgsConstructor
@Getter @Setter
class OrderDetail{
     String itemName;
     int cnt;
     int price;
     String orderId ;
}
