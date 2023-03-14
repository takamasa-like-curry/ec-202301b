package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.service.CartService;

@SpringBootTest
public class CartControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private CartController controller;
	@MockBean
	private CartService service;

	@BeforeEach
	public void setup() {
		// MockMvcオブジェクトにテスト対象メソッドを設定
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void showCartListTest() throws Exception {

		mockMvc.perform(get("/cart/showCartList")).andDo(print()).andExpect(status().isOk());

	}
}
