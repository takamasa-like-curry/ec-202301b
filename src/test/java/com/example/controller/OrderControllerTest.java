package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
//@WebMvcTest(controllers = OrderController.class)
@WithMockUser(username = "tanaka@gmail.com", password = "Tanaka2023", roles = "USER")
public class OrderControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private OrderController orderController;

	@BeforeEach()
	public void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	public void toOrderConfirmtest() throws Exception {
		System.out.println("toOrderConfirm()メソッドが正常に動いているかテストを行います。");

		mockMvc.perform(post("/order")).andExpect(status().isOk()).andExpect(redirectedUrl("/orderFinished"));

		System.out.println("問題ありませんでした。");

	}
}
