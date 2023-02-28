package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザー登録機能の処理の制御を行うコントローラ.
 * 
 * @author hayashiasuka
 *
 */
@Controller
@RequestMapping("/registerAdmin")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;

	/**
	 * 会員登録ページを表示する.
	 * 
	 * @return 会員登録ページ
	 */
	@GetMapping("")
	public String index() {
		return "register-admin";
	}

	/**
	 * ユーザー登録を行う.
	 * 
	 * @return
	 */
	@PostMapping("/registerUser")
	public String registerUser(RegisterUserForm registerUserForm) {

		// 登録済みEmailを確認する
		User user = registerUserService.searchByEmail(registerUserForm.getEmail());
		if (user == null) {
			// エラーメッセージ
		}

		// パスワードと確認用パスワードが一致しているか確認する
		if (registerUserForm.getPassword().equals(registerUserForm.getConfirmationPassword())) {
			// エラーメッセージ
		}

		registerUserService.registerUser(registerUserForm);

		return "forward:/LoginAndLogout";

	}

}
