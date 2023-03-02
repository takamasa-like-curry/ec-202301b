package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.form.AddItemForm;
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
public class CartService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 商品を追加する.
	 * 
	 * @param form   追加する商品の情報を取得するフォーム
	 * @param userId ユーザーID
	 * @return
	 */
	public Order addItem(AddItemForm form, Integer userId) {

		Integer statsu = 0;
		Order order = orderRepository.findByUserIdAndStatus(userId, statsu);

		// カートがない場合
		if (order == null) {
			order = new Order();
			order.setUserId(userId);
			order.setStatus(0);
			order.setTotalPrice(0);
			order = orderRepository.insert(order);

		}

		OrderItem orderItem = new OrderItem();
		Integer itemId = form.getItemId();
		orderItem.setItemId(itemId);
		orderItem.setOrderId(order.getId());
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		orderItem = orderItemRepository.insert(orderItem);

		if (form.getToppingIdList() != null) {

			for (Integer toppingId : form.getToppingIdList()) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setToppingId(toppingId);
				orderTopping.setOrderItemId(orderItem.getId());
				orderTopping = orderToppingRepository.insert(orderTopping);
			}

		}

		return order;

	}

	/**
	 * 商品情報を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return 取得した商品情報(order)
	 */
	public Order pickUpOrder(Integer userId) {
		Integer status = 0;
		Order order = orderRepository.findByUserIdAndStatus(userId, status);

		if (order == null) {
			order = new Order();
			order.setOrderItemList(new ArrayList<>());

			return order;
		}

		List<OrderItem> orderItemList = orderItemRepository.findByOrderId(order.getId());
		for (OrderItem orderItem : orderItemList) {
			orderItem.setItem(itemRepository.load(orderItem.getItemId()));
			List<OrderTopping> orderToppingList = orderToppingRepository.findByOrderItemId(orderItem.getId());
			for (OrderTopping orderTopping : orderToppingList) {
				Topping topping = toppingRepository.load(orderTopping.getToppingId());
				orderTopping.setTopping(topping);
			}
			orderItem.setOrderToppingList(orderToppingList);
		}
		order.setOrderItemList(orderItemList);
		return order;
	}

	/**
	 * 商品を削除する.
	 *  
	 * @param deelteItemId 削除する商品のID
	 */
	public void deleteOrderItem(Integer deelteItemId) {

		orderItemRepository.delete(deelteItemId);
	}
}
