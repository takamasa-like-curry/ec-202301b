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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private HttpSession session;
	@Autowired
	private CartService service;

	@GetMapping("/to")
	public String to() {
		return "cart_list";
	}

	@GetMapping("/detail")
	public String detail(AddItemForm form, Model model) {
		Item item = new Item();
		item.setId(1);
		model.addAttribute("item", item);
		return "item_detail";
	}

	@GetMapping("/showCartList")
	public String showCartList(Model model) {
		Integer userId = pickUpUserId();
		Order order = (Order) session.getAttribute("order");
//		order = service.pickUpOrder(order, userId);
//		if (order == null) {
//			model.addAttribute("cartStatusMessage","カート内に商品がありません");
//		}

		System.out.println(order);

		return "cart_list";
	}

	@PostMapping("/addItem")
	// モデルは削除すること
	public String addItem(@Validated AddItemForm form, BindingResult result, Model model) {

		// 入力値チェック
		if (result.hasErrors()) {
			return detail(form, model);
		}
		Integer userId = pickUpUserId();

		Order order = (Order) session.getAttribute("order");
		order = service.addItem(form, order, userId);

		session.setAttribute("order", order);
		return "redirect:/cart/showCartList";
	}

	/**
	 * ユーザーIDを取得
	 * 
	 * @return ログインしていればユーザーID、ログインしていなければ仮のユーザーID
	 */
	public Integer pickUpUserId() {
		Integer userId = null;
		User user = (User) session.getAttribute("user");
		// 未ログイン時の操作
		if (user == null) {
			String sessionId = session.getId();
			userId = sessionId.hashCode();
		} else {
			userId = user.getId();
		}
		return userId;
	}
}
