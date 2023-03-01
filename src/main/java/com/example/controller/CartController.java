package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.AddItemForm;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private HttpSession session;

	@GetMapping("/to")
	public String to() {
		return "cart_list";
	}

	@GetMapping("/detail")
	public String detail(AddItemForm form) {
		return "item_detail";
	}

	@PostMapping("/addItem")
	public String addItem(@Validated AddItemForm form, BindingResult result) {
		if (result.hasErrors()) {

			String sessionId = session.getId();
			int tentativeUserId = sessionId.hashCode();
			System.out.println(tentativeUserId);

			return detail(form);

		}

		return "";
	}
}
