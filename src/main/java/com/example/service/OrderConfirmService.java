package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

/**
 * 注文確認に関する業務処理を行うサービス
 * 
 * @author hayashiasuka
 *
 */
@Service
@Transactional
public class OrderConfirmService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	// 【6】注文確認画面表示
	/**
	 * 主キーで注文情報を取得する
	 * 
	 * @param id 注文ID
	 * @return 検索された注文情報
	 */
	public Order showOrderConfirm(Integer id) {

		// 注文情報の取得
		Order order = orderRepository.load(id);
		// 注文情報に注文商品の情報を組み込ませる
		order.setOrderItemList(orderItemRepository.findByOrderId(id));
		// 注文商品に注文トッピングの情報を組み込ませる
		for (OrderItem orderItem : order.getOrderItemList()) {
			orderItem.setOrderToppingList(orderToppingRepository.findByOrderItemId(orderItem.getId()));
		}

		return order;

	}

}
