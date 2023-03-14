package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

/**
 * ログイン・ログアウトに関する業務を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Service
@Transactional
public class LoginAndLogoutService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private HttpSession session;

	/**
	 * ログイン時に仮ユーザーIDの情報とユーザーIDの情報を統合.
	 * 
	 * @param loginUser ログインユーザー
	 * @return ユーザーID
	 */
	public Integer loginProcess(LoginUser loginUser) {
		Integer tentativeUserId = (Integer) session.getAttribute("userId"); // 注文時のユーザーID
		Integer userId = loginUser.getUser().getId(); // ユーザーID
		Integer tentativeOrderId = pickUpOrderId(tentativeUserId);
		Integer orderId = pickUpOrderId(userId);
		if (userId == tentativeUserId) {
			return userId;
		}

		if (orderId == null) {
			orderRepository.updateUserId(tentativeUserId, userId);
		} else {
			orderItemRepository.updateOrderId(tentativeOrderId, orderId);
			orderRepository.deleteOrderByOrderId(tentativeOrderId); // 仮のユーザーIDで使用したorderテーブルを削除
		}

		return userId;
	}

	/**
	 * 未注文(statusが0)のオーダーIDをユーザーIDで検索.
	 * 
	 * @param userId ユーザーID
	 * @return 該当オーダーID、なければnull
	 */

	public Integer pickUpOrderId(Integer userId) {
		Integer status = 0;
		Order order = orderRepository.findByUserIdAndStatus(userId, status);
		if (order == null) {
			return null;
		} else {
			return order.getId();
		}
	}
}
