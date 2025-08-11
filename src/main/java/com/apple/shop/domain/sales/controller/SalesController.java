package com.apple.shop.domain.sales.controller;

import com.apple.shop.domain.item.entity.Item;
import com.apple.shop.domain.item.service.ItemService;
import com.apple.shop.domain.sales.service.SalesService;
import com.apple.shop.view.ViewPath;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SalesController
{
    private final ItemService itemService;
    private final SalesService salesService;



    // 1. 주문 및 결제
    // 1.1 주문하기
    @PostMapping("/order")
    String order(Model model, @RequestParam Long itemId ){
        Optional<Item> opt = itemService.FindItem(itemId);

        if(opt.isPresent()){
            model.addAttribute("item", opt.get());
        }

        return ViewPath.SALES_ORDER;    // html return
    }
    @GetMapping("/order")
    public String orderPage(@RequestParam Long itemId, Model model, @ModelAttribute("error") String error) {
        itemService.FindItem(itemId).ifPresent(item -> model.addAttribute("item", item));
        model.addAttribute("error", error); // FlashAttribute로 넘긴 에러 메시지 표시
        return ViewPath.SALES_ORDER;
    }

    // 1.2 결제하기
    @PostMapping("/pay")
    String pay(Model model, @RequestParam Long itemId,
               @RequestParam int count,
               Authentication auth,
               RedirectAttributes ra) {
        try {
            salesService.createSalesRecord(itemId,count,auth);
            return ViewPath.REDIRECT_ITEM_LIST;
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            ra.addAttribute("itemId", itemId);
            return ViewPath.REDIRECT_SALES_ORDER;
        }
    }







    // 2. 주문 내역 조회
    @GetMapping("/list")
    String orderList(Model model){

        model.addAttribute("orders", salesService.getAllOrderDetailList());
        return ViewPath.SALES_LIST;
    }

}
