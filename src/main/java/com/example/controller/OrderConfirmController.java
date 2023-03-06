package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
//import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.LoginAndLogoutService;
import com.example.service.OrderConfirmService;

import jakarta.servlet.http.HttpSession;

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
	private HttpSession session;
	@Autowired
	private OrderConfirmService orderConfirmService;
	@Autowired
	private LoginAndLogoutService loginAndLogoutService;

	/**
	 * 注文確認画面を表示する.
	 * 
	 * @param orderId 注文ID
	 * @return 注文確認画面
	 */
	@GetMapping("")
	public String showOrderConfirm(Model model, OrderForm form, @AuthenticationPrincipal LoginUser loginUser) {
		////////////////////////////
		User user = loginUser.getUser();
		Integer tentativeUserId = (Integer) session.getAttribute("userId");
		Integer tentativeOrderId = loginAndLogoutService.pickUpOrderId(tentativeUserId);
		Integer orderId = loginAndLogoutService.pickUpOrderId(user.getId());
		if (tentativeOrderId != orderId) {

			if (orderId != null) {
				loginAndLogoutService.updateOrderItemId(tentativeOrderId, orderId);
				loginAndLogoutService.deleteOrderByOrderId(tentativeOrderId);
			} else {
				loginAndLogoutService.updateUserId(tentativeUserId, user.getId());
//			service.updateOrderItemId(tentativeOrderId, orderId);
				orderId = tentativeOrderId;
			}
		}

		///////////////////////////////

		Order order = orderConfirmService.showOrderConfirm(orderId);
		model.addAttribute("order", order);
		return "order_confirm";

	}
}
