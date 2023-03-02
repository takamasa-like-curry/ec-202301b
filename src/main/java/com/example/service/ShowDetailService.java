package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 詳細画面表示業務を行うサービス.
 * @author watanabe
 */
@Service
public class ShowDetailService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 主キーで商品を検索し、トッピングを持たせるメソッド.
	 * @param id
	 * @return　主キーにより検索されたitem
	 */
	public Item showDetailItem(Integer id) {
		
		Item item = itemRepository.load(id);
		List<Topping> toppingList = toppingRepository.findAll();
		item.setToppingList(toppingList);
		
		return item;

	}
}
