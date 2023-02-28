package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 * 商品一覧表示用コントローラ
 * 
 * @author watanabe
 *
 */
@Controller
@RequestMapping("")
public class ShowItemListCotroller {
	@Autowired
	private ShowItemListService showItemListService;

	/**
	 * 商品情報を検索します.
	 * 
	 * @param model
	 * @retur 商品一覧に遷移します
	 */
	@GetMapping("/showList")
	public String showList(Model model) {
		List<Item> itemList = showItemListService.showItemList();
		model.addAttribute("itemList", itemList);
		return "item_list";
	}
}
