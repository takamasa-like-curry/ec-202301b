package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;

import jakarta.servlet.http.HttpSession;

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
	
	/**
	 * 注文確認画面を表示する.
	 * 
	 * @param orderId 注文ID
	 * @return 注文確認画面
	 */
	@GetMapping("")
	public String showOrderConfirm(Integer orderId, Model model,OrderForm form,String key) {
		User user = (User)session.getAttribute("user");
		System.out.println("==============");
		System.out.println("orderConfirmControllerのshowOrderConfirm");
		System.out.println(key);
		System.out.println("==============");
		System.out.println("==============");
		
		if(user == null) {
			return "forward:/loginAndLogout";
		}
		Order order = orderConfirmService.showOrderConfirm(orderId);
		model.addAttribute("order", order);
		return "order_confirm";
		
	}
}
