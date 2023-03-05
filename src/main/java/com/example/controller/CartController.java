package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.AddItemForm;
import com.example.service.CartService;
import com.example.service.ShowDetailService;

import jakarta.servlet.http.HttpSession;

/**
 * ショッピンカート内の操作を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private HttpSession session;
	@Autowired
	private CartService service;
	@Autowired
	private ShowDetailService showDetailService;

	/**
	 * 商品詳細画面を表示(入力値チェックの際に使用).
	 * 
	 * @param model  モデル
	 * @param itemId 元ページで表示されていた商品のID
	 * @param form   フォーム
	 * @return 商品詳細画面
	 */
	public String toDetail(Model model, Integer itemId, AddItemForm form) {
		Item item = showDetailService.showDetailItem(itemId);
		model.addAttribute("item", item);
		return "item_detail";
	}

	/**
	 * ショッピングカート画面を表示.
	 * 
	 * @param model モデル
	 * @return ショッピングカート画面
	 */
	@GetMapping("/showCartList")
	public String showCartList(Model model) {
		Integer userId = pickUpUserId();
		Order order = service.pickUpOrder(userId);
		model.addAttribute("order", order);
		model.addAttribute("key", "toOrderConfirm"); // enum検討

		return "cart_list";
	}

	/**
	 * 商品を追加する.
	 * 
	 * @param form   フォーム
	 * @param result リザルト
	 * @param model  モデル
	 * @return ショッピングカート画面
	 */
	@PostMapping("/addItem")
	public String addItem(@Validated AddItemForm form, BindingResult result, Model model) {
		System.out.println(result);
		// 入力値チェック
		if (result.hasErrors()) {
			return toDetail(model, form.getItemId(), form);
		}
		Integer userId = pickUpUserId();

		service.addItem(form, userId);

		return "redirect:/cart/showCartList";
	}

	/**
	 * ユーザーIDを取得
	 * 
	 * @return
	 */
	public Integer pickUpUserId() {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			String sessionId = session.getId();
			Integer userId = sessionId.hashCode();
			return userId;
		}

		return user.getId();
	}

	/**
	 * 商品を削除.
	 * 
	 * @param deleteItemId 削除する商品のID
	 * @return ショッピングカート画面
	 */
	@PostMapping("/deleteItem")
	public String deleteItem(Integer deleteItemId) {

		service.deleteOrderItem(deleteItemId);
		return "redirect:/cart/showCartList";
	}

}
