package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.OrderForm;
import com.example.service.CartService;
import com.example.service.LoginAndLogoutService;


//import jakarta.servlet.http.HttpSession;

/**
 * 注文確認画面表示の機能を制御するコントローラ.
 * 
 * @author hayashiasuka
 *
 */
@Controller
@RequestMapping("/orderConfirm")
public class OrderConfirmController {

	@Autowired
	private LoginAndLogoutService loginAndLogoutService;
	@Autowired
	private CartService cartService;

	/**
	 * 注文確認画面を表示する.
	 * 
	 * @param orderId 注文ID
	 * @return 注文確認画面
	 */
	@GetMapping("")
	public String showOrderConfirm(Model model, OrderForm form, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = loginAndLogoutService.loginProcess(loginUser);

		Order order = cartService.pickUpOrder(userId);
		model.addAttribute("order", order);
		model.addAttribute("key", loginUser.getUser().getEmail().hashCode());
		return "order_confirm";

	}
}
