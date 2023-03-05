package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * トップページの表示の機能の処理を制御するコントローラ.
 * 
 * @author hayashiasuka
 *
 */
@Controller
@RequestMapping("/")
public class TopController {

	/**
	 * トップページを表示する.
	 * 
	 * @return トップページ
	 */
	@GetMapping("")
	public String index() {
		return "top";
	}
}
