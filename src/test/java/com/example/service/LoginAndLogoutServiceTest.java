package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginAndLogoutServiceTest {

	@InjectMocks
	private LoginAndLogoutService service;
	@Mock
	private HttpSession session;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderItemRepository orderItemRepository;

	private LoginUser loginUser;

	@BeforeAll
	public void before() {
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		User user = new User();
		user.setId(1);
		user.setEmail("aa");
		user.setPassword("aa");
		this.loginUser = new LoginUser(user, authorityList);

	}

	/**
	 * ログイン後カートに入れた際のテスト.
	 * 
	 */
	@Test
	public void loginProcessTest1() {
		Mockito.when(session.getAttribute("userId")).thenReturn(1);

		Integer result = service.loginProcess(loginUser);

		assertEquals(result, 1, "ログインプロセスにてエラーが発生しています。");
	}

	@Test
	public void loginProcessTest2() {
		System.out.println("loginProcessTest2開始");
		Mockito.when(session.getAttribute("userId")).thenReturn(123);
		Mockito.when(orderRepository.findByUserIdAndStatus(123, 0)).thenReturn(null);
		Mockito.when(orderRepository.findByUserIdAndStatus(1, 0)).thenReturn(null);
		Integer result = service.loginProcess(loginUser);
		assertEquals(result, 1, "IDの結果が違います。");
		Mockito.verify(orderRepository).updateUserId(123, 1);

		System.out.println("loginProcessTest2終了");
	}

	@Test
	public void loginProcessTest3() {
		System.out.println("loginProcessTest3開始");
		Mockito.when(session.getAttribute("userId")).thenReturn(123);
		Order order1 = new Order();
		order1.setId(123);
		Mockito.when(orderRepository.findByUserIdAndStatus(123, 0)).thenReturn(order1);
		Order order2 = new Order();
		order2.setId(1);
		Mockito.when(orderRepository.findByUserIdAndStatus(1, 0)).thenReturn(order2);
		Integer result = service.loginProcess(loginUser);
		assertEquals(result, 1, "IDの結果が違います。");
		Mockito.verify(orderItemRepository).updateOrderId(123, 1);
		Mockito.verify(orderRepository).deleteOrderByOrderId(123);

		System.out.println("loginProcessTest3終了");
	}

}
