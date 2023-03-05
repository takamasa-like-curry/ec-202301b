package com.example.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;
import com.example.service.OrderService;

import jakarta.servlet.http.HttpSession;

/**
 * 注文処理を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderConfirmService orderConfirmService;
	@Autowired
	private OrderService orderServise;
	@Autowired
	private HttpSession session;

	/**
	 * 入力漏れ時に入力画面に遷移.
	 * 
	 * @param orderId オーダーID
	 * @param model   モデル
	 * @return 注文情報入力画面
	 */
	public String toOrderConfirm(Integer orderId, Model model) {
		Order order = orderConfirmService.showOrderConfirm(orderId);
		model.addAttribute("order", order);
		return "order_confirm";
	}

	/**
	 * 注文を完了させる.
	 * 
	 * @param form   配達情報フォーム.
	 * @param result リザルト
	 * @param model  モデル
	 * @return 注文完了画面
	 */
	@PostMapping("")
	public String order(@Validated OrderForm form, BindingResult result, Model model) {
		System.out.println(form);
		// 入力値チェック
		result = addDeliveryTimeError(form, result);
		if (result.hasErrors()) {

			return toOrderConfirm(form.getOrderId(), model);
		}

		// ユーザー情報取得
//		User user = (User) session.getAttribute("user");
		User user = (User) session.getAttribute("user");

		orderServise.order(form, user.getId());

		return "redirect:/orderFinished";
	}

	public BindingResult addDeliveryTimeError(OrderForm form, BindingResult result) {
		// 入力値チェック(配達日時)
		LocalDate deliveryDate = LocalDate.parse(form.getDeliveryDate());
		LocalDateTime deliveryDateTime = LocalDateTime
				.parse(form.getDeliveryDate() + "T" + form.getDeliveryTime() + ":00:00");

		if (deliveryDate.isBefore(LocalDate.now())) {

			result.rejectValue("deliveryDate", null, "配達日が本日より前の日付になっています。");
			return result;

		} else if (deliveryDateTime.isBefore(LocalDateTime.now().plusHours(3))) {
			LocalDateTime nowTime = LocalDateTime.now();
			LocalDateTime limitTime = LocalDateTime.now().withHour(15).withMinute(0).withSecond(0).withNano(0);
			if (nowTime.isBefore(limitTime)) {
				Integer availableTIme = nowTime.getHour() + 4;
				result.addError(new FieldError("orderform", "deliveryTime",
						"配達時間をご確認ください（現在、" + availableTIme + "時以降に配達が可能です。)"));
//				result.rejectValue("deliveryTime", null, "配達時間をご確認ください（現在、" + availableTIme + "時以降に配達が可能です。)");
			} else {
				result.addError(new FieldError("orderform", "deliveryTime", "本日の予約可能時間を過ぎています。明日以降の日時を選択してください。"));
//				result.rejectValue("deliveryTime", null, "本日の予約可能時間を過ぎています。明日以降の日時を選択してください。");

			}
			return result;
		} else {
			return result;
		}

	}
}
