package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.repository.ItemRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;
import com.example.repository.ToppingRepository;

/**
 * カート操作の業務処理を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */

@Service
@Transactional
public class CompleteOrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 全ての情報を詰め込んだOrderオブジェクトを作成する.
	 * 
	 * @return 全ての情報が詰まったOrderオブジェクト
	 */
	public Order completeOrder(Integer id) {

		// 注文情報の取得
		Order order = orderRepository.load(id);

		// 注文情報に注文商品の情報を組み込ませる
		order.setOrderItemList(orderItemRepository.findByOrderId(id));

		for (OrderItem orderItem : order.getOrderItemList()) {
			// 注文商品に商品情報を組み込ませる
			orderItem.setItem(itemRepository.load(orderItem.getItemId()));

			// 注文商品に注文トッピングの情報を組み込ませる
			orderItem.setOrderToppingList(orderToppingRepository.findByOrderItemId(orderItem.getId()));

			for (OrderTopping orderTopping : orderItem.getOrderToppingList()) {
				// 注文トッピングにトッピングの情報を組み込ませる
				orderTopping.setTopping(toppingRepository.load(orderTopping.getToppingId()));
			}
		}

		return order;
	}

}
