package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/registerUser")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;

	/**
	 * 会員登録ページを表示する.
	 * 
	 * @return 会員登録ページ
	 */
	@GetMapping("")
	public String index(Model model, RegisterUserForm registerUserForm) {
		return "register_user";
	}

	/**
	 * ユーザー登録を行う.
	 * 
	 * @return ログイン画面
	 */
	@PostMapping("/register")
	public String registerUser(@Validated RegisterUserForm registerUserForm, BindingResult result, Model model) {

		// 登録済みEmailを確認する
		User user = registerUserService.searchByEmail(registerUserForm.getEmail());
		if (user != null) {
			result.rejectValue("email", null, "すでに登録されているメールアドレスです");
		}

		// パスワードと確認用パスワードが一致しているか確認する
		if (!(registerUserForm.getPassword().equals(registerUserForm.getConfirmationPassword()))) {
			result.rejectValue("confirmationPassword", null, "パスワードと確認用パスワードが不一致です");
		}

		//エラーメッセージの表示・入力情報の保持
		if(result.hasErrors()) {
			return index(model, registerUserForm);
		}
		
		//ユーザー登録処理
		registerUserService.registerUser(registerUserForm);

		return "redirect:/loginAndLogout";

	}

}
