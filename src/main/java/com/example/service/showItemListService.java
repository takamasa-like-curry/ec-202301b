package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品一覧を操作するサービス.
 * @author watanabe
 *
 */
public class showItemListService {

	@Autowired
	ItemRepository itemRepository;
	
	/**
	 * 商品情報をすべて取得します.
	 * @return　商品一覧
	 */
	public List<Item> showItemList(){
		List<Item> itemList= itemRepository.findAll();
		return itemList;
	}
}
