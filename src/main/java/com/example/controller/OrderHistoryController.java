package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.service.OrderHistoryService;

/**
 * 注文履歴画面表示の機能の処理を行うコントローラ.
 * 
 * @author hayashiasuka
 *
 */
@Controller
@RequestMapping("/orderHistory")
public class OrderHistoryController {

	@Autowired
	private OrderHistoryService orderHistoryService;

	/**
	 * 注文履歴画面を表示する.
	 * 
	 * @param userId ユーザーID
	 * @param model  モデル
	 * @return 注文履歴画面
	 */
	@GetMapping("")
	public String showOrderHistory(Integer userId, Model model) {

		List<Order> orderList = orderHistoryService.showOrderHistory(userId);
		model.addAttribute("orderList", orderList);

		return "order_history";
	}
	
	@GetMapping("/detail")
	public String showOrderHistoryDetail(Integer id, Model model) {
		
		Order order = orderHistoryService.showOrderHistoryDetail(id);
		model.addAttribute(order);
		
		return "order_history_detail";
	}
}
