package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.form.RegisterUserForm;

/**
 * RegisterUserControllerクラスのテストを行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class RegisterUserControllerTest {

	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	private RegisterUserController controller;
	private MockMvc mockMvc;

	@BeforeEach
	public void beforeEach() {
		// モックの初期化
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		// DBの初期化
		String sql = "truncate table users;";
		template.update(sql, new MapSqlParameterSource());
	}

	@AfterAll
	public void afterAll() {
		String sql = "truncate table users;";
		template.update(sql, new MapSqlParameterSource());

	}

	@Test
	public void 会員登録ページへの遷移() throws Exception {
		System.out.println("【会員登ページへの遷移】テスト開始");

		mockMvc.perform(get("/registerUser")).andExpect(status().isOk()).andExpect(view().name("register_user"));
		System.out.println("【会員登ページへの遷移】テスト終了");
	}

	@Test
	public void 登録_正常() throws Exception {
		System.out.println("【登録_正常】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setLastName("菅原");
		form.setFirstName("隆正");
		form.setEmail("takamasa@gmail.com");
		form.setZipcode("111-1111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("Takamasa2023");
		form.setConfirmationPassword("Takamasa2023");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
				.andExpect(model().hasNoErrors()) // エラーがないか？
				.andExpect(status().is3xxRedirection()) // ステータスコードのチェック
				.andExpect(redirectedUrl("/loginAndLogout")); // リダイレクト先のURLをチェック

		System.out.println("【登録_正常】テスト終了");
	}

	@Test
	public void 登録_名字が空白() throws Exception {
		System.out.println("【登録_名字が空白】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setFirstName("隆正");
		form.setEmail("takamasa@gmail.com");
		form.setZipcode("111-1111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("Takamasa2023");
		form.setConfirmationPassword("Takamasa2023");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
//				.andDo(print()) // 出力
				.andExpect(status().isOk()) // ページ遷移に問題がないか
				.andExpect(view().name("register_user")) // 入力画面に戻っているか：これであっているのか？
				.andExpect(model().errorCount(1)) // モデル内のエラーの数をチェック
				.andExpect(model().attributeErrorCount("registerUserForm", 1)) // フォーム内のエラーの数をチェック
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "lastName")); // エラーが出ている箇所に問題がないか

		System.out.println("【登録_名字が空白】テスト終了");
	}

	@Test
	public void 登録_名前が空白() throws Exception {
		System.out.println("【登録_名前が空白】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setLastName("菅原");
//		form.setFirstName("隆正");
		form.setEmail("takamasa@gmail.com");
		form.setZipcode("111-1111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("Takamasa2023");
		form.setConfirmationPassword("Takamasa2023");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
				.andExpect(status().isOk()) //
				.andExpect(view().name("register_user")) // 入力画面に戻っているか：これであっているのか？
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "firstName")) // エラーがないか？
				.andExpect(model().attributeErrorCount("registerUserForm", 1)); // エラーの数をチェック

		System.out.println("【登録_名前が空白】テスト終了");
	}

	@Test
	public void 登録_メールアドレスが空白() throws Exception {
		System.out.println("【登録_メールアドレスが空白】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setLastName("菅原");
		form.setFirstName("隆正");
		form.setZipcode("111-1111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("Takamasa2023");
		form.setConfirmationPassword("Takamasa2023");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
				.andExpect(status().isOk()) //
				.andExpect(view().name("register_user")) // 入力画面に戻っているか：これであっているのか？
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "email")) // エラーがないか？
				.andExpect(model().attributeErrorCount("registerUserForm", 1)); // エラーの数をチェック

		System.out.println("【登録_メールアドレスが空白】テスト終了");
	}

	@Test
	public void 登録_郵便番号が不正() throws Exception {
		System.out.println("【登録_郵便番号が不正】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setLastName("菅原");
		form.setFirstName("隆正");
		form.setEmail("takamasa@gmail.com");
		form.setZipcode("1111111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("Takamasa2023");
		form.setConfirmationPassword("Takamasa2023");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
				.andExpect(status().isOk()) //
				.andExpect(view().name("register_user")) // 入力画面に戻っているか：これであっているのか？
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "zipcode")) // エラーの箇所をチェック
				.andExpect(model().attributeErrorCount("registerUserForm", 1)); // エラーの数をチェック

		System.out.println("【登録_郵便番号が不正】テスト終了");
	}

	@Test
	public void 登録_パスワードが不正1() throws Exception {
		System.out.println("【登録_パスワードが不正1】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setLastName("菅原");
		form.setFirstName("隆正");
		form.setEmail("takamasa@gmail.com");
		form.setZipcode("111-1111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("takamasa2023");
		form.setConfirmationPassword("takamasa2023");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
				.andExpect(status().isOk()) //
				.andExpect(view().name("register_user")) // 入力画面に戻っているか：これであっているのか？
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "password")) // エラーの箇所をチェック
				.andExpect(model().attributeErrorCount("registerUserForm", 1)); // エラーの数をチェック

		System.out.println("【登録_パスワードが不正1】テスト終了");
	}

	@Test
	public void 登録_パスワードが不正2() throws Exception {
		System.out.println("【登録_パスワードが不正2】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setLastName("菅原");
		form.setFirstName("隆正");
		form.setEmail("takamasa@gmail.com");
		form.setZipcode("111-1111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("takamasatakamasa");
		form.setConfirmationPassword("takamasatakamasa");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
				.andExpect(status().isOk()) //
				.andExpect(view().name("register_user")) // 入力画面に戻っているか：これであっているのか？
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "password")) // エラーの箇所をチェック
				.andExpect(model().attributeErrorCount("registerUserForm", 1)); // エラーの数をチェック

		System.out.println("【登録_パスワードが不正2】テスト終了");
	}

	@Test
	public void 登録_パスワードが不正3() throws Exception {
		System.out.println("【登録_パスワードが不正3】テスト開始");

		RegisterUserForm form = new RegisterUserForm();
		form.setLastName("菅原");
		form.setFirstName("隆正");
		form.setEmail("takamasa@gmail.com");
		form.setZipcode("111-1111");
		form.setAddress("京都府");
		form.setTelephone("090-9999-9999");
		form.setPassword("1234567890");
		form.setConfirmationPassword("1234567890");

		mockMvc.perform(post("/registerUser/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("lastName", form.getLastName()).param("firstName", form.getFirstName())
				.param("email", form.getEmail()).param("zipcode", form.getZipcode()).param("address", form.getAddress())
				.param("telephone", form.getTelephone()).param("password", form.getPassword())
				.param("confirmationPassword", form.getConfirmationPassword()))// ここまでフォーム
				.andExpect(status().isOk()) //
				.andExpect(view().name("register_user")) // 入力画面に戻っているか：これであっているのか？
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "password")) // エラーの箇所をチェック
				.andExpect(model().attributeErrorCount("registerUserForm", 1)); // エラーの数をチェック

		System.out.println("【登録_パスワードが不正3】テスト終了");
	}

}
