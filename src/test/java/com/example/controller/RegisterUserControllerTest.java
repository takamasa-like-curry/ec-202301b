package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class RegisterUserControllerTest {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private MockMvc mockMvc;
	private RegisterUserForm form;
	@Autowired
	private RegisterUserController controller;

	@BeforeEach
	public void beforeEach() {
		// モックの初期化
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		// フォーム情報の初期化

		// DBの初期化
		String sql = "truncate table users;";
		template.update(sql, new MapSqlParameterSource());

	}

	@Test
	public void index() throws Exception {

		mockMvc.perform(get("/registerUser")).andDo(print()).andExpect(status().isOk())
				.andExpect(view().name("register_user"));
	}

	/**
	 * 問題ない時のテスト.
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerUserTest() throws Exception {
		System.out.println("フォームを受け取る方のテストを始めます。");

		form = new RegisterUserForm();
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
				.andExpect(status().is3xxRedirection()) // リダイレクトされているか？
				.andExpect(model().hasNoErrors()) // エラーがないか？
				.andDo(print()); // 出力

	}

	@Test
	public void registerUserTest2() throws Exception {
		System.out.println("フォームを受け取る方のテストを始めます。②");

		form = new RegisterUserForm();
		form.setLastName("菅原");
		form.setFirstName("隆正");
		form.setEmail("takamasa0106@gmail.com");
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
				.andDo(print()) // 出力
				.andExpect(status().isOk()) // リダイレクトされているか？
				.andExpect(model().hasErrors()) // エラーがないか？
				.andExpect(model().attributeHasFieldErrors("registerUserForm", "zipcode"));

	}

}
