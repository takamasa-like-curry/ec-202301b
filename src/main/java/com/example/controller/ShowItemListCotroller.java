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
	 * @param model モデル
	 * @return 商品一覧に遷移します
	 */
	@GetMapping("/showList")
	public String showList(Model model, String name) {
		List<Item> itemList = showItemListService.showItemList(name);
	//アイテムリストが空だった場合
		if (itemList.isEmpty()) { 
			String message = "該当する商品がありません";
			model.addAttribute("message", message);
	//nameをnullにして、Serviceクラスの全件検索を呼び出し、商品全件の情報が入ったItemListをmodelに渡す
			itemList = showItemListService.showItemList(null);
		}
		model.addAttribute("itemList", itemList);
		return "item_list";
	}
}
