package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.form.LoginForm;
import com.example.service.LoginAndLogoutService;

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

	@Autowired
	private HttpSession session;
	@Autowired
	private LoginAndLogoutService loginAndLogoutService;

	/**
	 * ログイン失敗時に、ログイン画面を表示.
	 * 
	 * @param form  ログイン情報を取得するフォーム
	 * @param model モデル
	 * @param error ログインが失敗した際の判定用指針(errorがtrueなら失敗)
	 * @return ログイン画面(エラーメッセージ有)
	 */
	@GetMapping("")
	public String toLogin(LoginForm form, Model model, boolean error) {

		if (error) {
			model.addAttribute("loginErrorMessage", "メールアドレスまたはパスワードが不正です。");
		}
		model.addAttribute("sessionId", session.getId());
		return "login";
	}

	/**
	 * ログイン処理を行い商品一覧ページへ遷移する.
	 * 
	 * @param loginUser ログインユーザー
	 * @return
	 */
	@GetMapping("/toShowList")
	public String toShowList(@AuthenticationPrincipal LoginUser loginUser) {

		loginAndLogoutService.loginProcess(loginUser);

		return "redirect:/showList";

	}

}
