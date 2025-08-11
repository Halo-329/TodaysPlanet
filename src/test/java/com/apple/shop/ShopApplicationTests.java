package com.apple.shop;

import com.apple.shop.domain.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ShopApplicationTests {

	@Autowired
	private ItemService itemService;

	@Test
	void contextLoads() {
		System.out.println(1);
	}


	@Test
	@Transactional
	void test(){
		itemService.savaItem("1", 12,3, null,"khan","dd");
	}

}




