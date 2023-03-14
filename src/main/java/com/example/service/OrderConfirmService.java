package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;

/**
 * 注文確認に関する業務処理を行うサービス
 * 
 * @author hayashiasuka
 *
 */
@Service
@Transactional
public class OrderConfirmService extends CompleteOrderService{


	// 【6】注文確認画面表示
	/**
	 * 主キーで注文情報を取得する
	 * 
	 * @param id 注文ID
	 * @return 検索された注文情報
	 */
	public Order showOrderConfirm(Integer id) {
		
		Order order = super.completeOrder(id);

		return order;

	}

}
