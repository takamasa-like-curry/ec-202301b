package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 注文確認画面を表示させるクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Controller
@RequestMapping("/orderFinished")
public class OrderFInishedController {

	
	/**
	 * 注文確認画面を表示.
	 * 
	 * @return
	 */
	@GetMapping("")
	public String toOrderFinished() {
		return "order_finished";
	}
}
