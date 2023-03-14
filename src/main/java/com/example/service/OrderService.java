package com.example.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderRepository;
import com.example.form.OrderForm;

/**
 * カート操作の業務処理を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 注文を実行する.
	 * 
	 * @param form   注文情報フォーム
	 * @param userId ユーザーID
	 */
	public void order(OrderForm form, Integer userId) {
		Order order = orderRepository.load(form.getOrderId());
		BeanUtils.copyProperties(form, order);

		order.setUserId(userId);
		order.setStatus(4); // 配達完了
		order.setOrderDate(new Date());
		LocalDateTime deliveryTime = LocalDateTime.parse(form.getDeliveryDate() + "T" + form.getDeliveryTime() + ":00");
		order.setDeliveryTime(deliveryTime);
		orderRepository.update(order);
	}

}
