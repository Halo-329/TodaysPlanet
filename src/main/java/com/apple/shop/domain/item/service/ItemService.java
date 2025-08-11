package com.apple.shop.domain.item.service;

import com.apple.shop.domain.item.entity.Item;
import com.apple.shop.domain.item.repo.ItemRepository;
import com.apple.shop.domain.item.validator.ItemValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator = new ItemValidator();

    public boolean savaItem(String title, Integer price, int stock, Model model, String usrID, String imageURL) {
        Item item = new Item();

        if (!itemValidator.validateInput(title, price)) {
            model.addAttribute("error", "제목이 20자 이상이거나 가격이 음수입니다.");
            return false;
        } else {
            item.setTitle(title);
            item.setPrice(price);
            item.setUsrID(usrID);
            item.setStock(stock);
            item.setImageURL(imageURL);
            itemRepository.save(item);
            return true;
        }
    }
    public boolean ChangeItem(Long id, String title, Integer price, Model model) {
        Item item;

        Optional<Item> opt = FindItem(id);


        if (!itemValidator.validateInput(title, price)) {
            return false;
        } else {
            if (opt.isPresent()) {
                item = opt.get();
                item.setTitle(title);
                item.setPrice(price);
                itemRepository.save(item);
            }
            else{
                return false;
            }
        }
        return true;
    }
    public void DeleteItem(Long id){
        Optional<Item> opt=FindItem(id);
        if (opt.isPresent()) {
            itemRepository.deleteById(id);
        }
    }
    public List<Item> GetItemList() {
        return itemRepository.findAll();
    }
    public Optional<Item> FindItem(Long id) {
        return itemRepository.findById(id);
    }


    // 2. 페이지네이션
    public Page<Item> getPage(int page_num, int item_cnt){
        return itemRepository.findPageBy(PageRequest.of(page_num, item_cnt));
    }

    // 3.1. 일반 검색
    public List<Item> getSearchItemsList(String searchText){
        return itemRepository.findAllByTitleContains(searchText);
    }

    // 3.2. Full Text Index 검색
    public Page<Item> findItemByTitle(String searchText, int page_num, int item_cnt){
        return itemRepository.findAllItemByTitle(searchText, PageRequest.of(page_num, item_cnt));
    }




    // 4. 주문시 재고 계산
    @Transactional
    public void reduceStock(Long id, int stock_count){
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Item 존재 안함"));

        item.setStock(item.getStock()-stock_count); // 재고 감소
        }
}
