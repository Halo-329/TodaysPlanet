package com.apple.shop.domain.item.repo;

import com.apple.shop.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//테이블 인터페이스
public interface ItemRepository extends JpaRepository<Item,Long> {

    // 1. 페이지네이션
    Page<Item> findPageBy(Pageable page);

    // 2.1 검색
    List<Item> findAllByTitleContains(String searchText);

    // 2.2 Full Text Index 사용하여 검색
    @Query(value="select * from item where match(title) against(?1)", nativeQuery = true)
    Page<Item>  findAllItemByTitle(String searchText, Pageable page);


}
