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

	public Order addItem(AddItemForm form, Integer userId) {
		
		Integer statsu = 0;
		Order order = orderRepository.findByUserIdAndStatus(userId, statsu);//ここに
		
		// カートがない場合
		if (order == null) {
			System.out.println("order存在しない");
			order = new Order();
			order.setUserId(userId);
			order.setStatus(0);
			order.setTotalPrice(0);
			order = orderRepository.insert(order);
			order.setOrderItemList(new ArrayList<OrderItem>());
			System.out.println(order);
		}

		OrderItem orderItem = new OrderItem();
		Integer itemId = form.getItemId();
		orderItem.setItemId(itemId);
		orderItem.setOrderId(order.getId());
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		orderItem = orderItemRepository.insert(orderItem);
		orderItem.setItem(itemRepository.load(itemId));

		List<OrderTopping> orderToppingList = new ArrayList<>();
		if (form.getToppingIdList() != null) {

			for (Integer toppingId : form.getToppingIdList()) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setToppingId(toppingId);
				orderTopping.setOrderItemId(orderItem.getId());
				orderTopping = orderToppingRepository.insert(orderTopping);
				orderTopping.setTopping(toppingRepository.load(toppingId));
				orderToppingList.add(orderTopping);
			}

		}
		orderItem.setOrderToppingList(orderToppingList);
		List<OrderItem> orderItemList = order.getOrderItemList();
		orderItemList.add(orderItem);
		order.setOrderItemList(orderItemList);

		return order;

	}

	public Order pickUpOrder(Integer userId) {
		Integer status = 0;
		Order order = orderRepository.findByUserIdAndStatus(userId, status);
		
		if(order == null) {
			order = new Order();
			order.setOrderItemList(new ArrayList<>());
			
			return order;
		}

		List<OrderItem> orderItemList = order.getOrderItemList();
		for (OrderItem orderItem : orderItemList) {
			orderItem.setItem(itemRepository.load(orderItem.getId()));
			List<OrderTopping> orderToppingList = orderItem.getOrderToppingList();
			for (OrderTopping orderTopping : orderToppingList) {
				Topping topping = toppingRepository.load(orderTopping.getId());
				orderTopping.setTopping(topping);
			}
			orderItem.setOrderToppingList(orderToppingList);
		}
		order.setOrderItemList(orderItemList);
		return order;
	}

	public void deleteOrderItem(Integer deelteItemId) {
		orderItemRepository.delete(deelteItemId);
	}
}
