package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.LoginForm;

//import com.example.domain.User;
//import com.example.service.LoginAndLogoutService;

import jakarta.servlet.http.HttpSession;

/**
 * ログイン・ログアウトを行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Controller
@RequestMapping("/loginAndLogout")

public class LoginAndLogoutController {
//	@Autowired
//	private LoginAndLogoutService service;

	@Autowired
	private HttpSession session;

	@GetMapping("")
	public String toLogin(LoginForm form, Model model, String error) {
		if (error != null) {
			model.addAttribute("loginErrorMessage", "メールアドレスまたはパスワードが不正です。");
			
		}
		model.addAttribute("sessionId", session.getId());
		return "login";
	}



//
//	@GetMapping("/logout")
//
//	public String logout() {
//		session.removeAttribute(null);
//		return "redirect:/";
//	}
}
