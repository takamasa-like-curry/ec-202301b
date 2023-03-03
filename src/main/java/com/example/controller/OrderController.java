package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;
import com.example.service.OrderService;

import jakarta.servlet.http.HttpSession;

/**
 * 注文処理を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderConfirmService orderConfirmService;
	@Autowired
	private OrderService orderServise;
	@Autowired
	private HttpSession session;

	/**
	 * 入力漏れ時に入力画面に遷移.
	 * 
	 * @param orderId オーダーID
	 * @param model   モデル
	 * @return 注文情報入力画面
	 */
	public String toOrderConfirm(Integer orderId, Model model) {
		Order order = orderConfirmService.showOrderConfirm(orderId);
		model.addAttribute("order", order);
		return "order_confirm";
	}

	/**
	 * 注文を完了させる.
	 * 
	 * @param form   配達情報フォーム.
	 * @param result リザルト
	 * @param model  モデル
	 * @return 注文完了画面
	 */
	@PostMapping("")
	public String order(@Validated OrderForm form, BindingResult result, Model model) {
		// 入力値チェック
		if (result.hasErrors()) {
			return toOrderConfirm(form.getOrderId(), model);
		}
		// ユーザー情報取得
		User user = (User) session.getAttribute("user");

		orderServise.order(form, user.getId());

		return "redirect:/orderFinished";
	}
}
