package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 注文履歴表示の業務処理をするサービス.
 * 
 * @author hayashiasuka
 *
 */
@Service
@Transactional
public class OrderHistoryService extends CompleteOrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * ユーザーIDで注文情報を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> showOrderHistory(Integer userId) {

		List<Order> orderList = orderRepository.findByUserId(userId);
		List<Order> newOrderList = new ArrayList<>();
		for (Order order : orderList) {
			order = super.completeOrder(order.getId());
			newOrderList.add(order);
		}
		
		return newOrderList;

	}
	
	/**
	 * 注文IDで注文情報を取得する.
	 * 
	 * @param id 注文ID
	 * @return 検索された注文情報
	 */
	public Order showOrderHistoryDetail(Integer id) {
		
		Order order = super.completeOrder(id);
		return order;
	}

}
