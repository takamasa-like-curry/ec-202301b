package com.example.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
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
	@Autowired
	private OrderConfirmService orderConfirmService;

	public void order(OrderForm form, Integer userId) {
		Order order = orderRepository.load(form.getOrderId());
//		order.setTotalPrice(form.getTotalPrice());
//		order.setDestinationName(form.getDestinationName());
//		order.setDestinationEmail(form.getDestinationEmail());
//		order.setDestinationZipcode(form.getDestinationZipcode());
//		order.setDestinationAddress(form.getDestinationAddress());
//		order.setDestinationTel(form.getDestinationTel());
//		order.setPaymentMethod(form.getPaymentMethod());
		BeanUtils.copyProperties(form, order);

		order.setUserId(userId);
		order.setStatus(4); // 配達完了
		order.setOrderDate(new Date());
		LocalDateTime deliveryTime = LocalDateTime.parse(form.getDeliveryDate() + "T" + form.getDeliveryTime() + ":00");
		order.setDeliveryTime(deliveryTime);
		orderRepository.update(order);
	}

	public void updateUserId(Integer orderId, Integer tentativeUserId) {
		Order tentativeOrder = null;

		Order order = orderConfirmService.showOrderConfirm(orderId);
		tentativeOrder = orderConfirmService.showOrderConfirm(tentativeUserId);
		List<OrderItem> tentativeOrderItemList = tentativeOrder.getOrderItemList();
		for (OrderItem tentativeOrderItem : tentativeOrderItemList) {
			order.getOrderItemList().add(tentativeOrderItem);
		}
		
	}

}
