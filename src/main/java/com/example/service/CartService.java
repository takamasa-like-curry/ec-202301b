package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.form.AddItemForm;
import com.example.repository.OrderRepository;

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
	
//	public Order addCart(AddItemForm form) {
//		
//	}
}
