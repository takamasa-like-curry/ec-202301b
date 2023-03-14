package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.domain.Order;

@SpringBootTest
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private NamedParameterJdbcTemplate template;

	Integer orderId = null;

	@BeforeEach
	public void testInsert() {
		System.out.println("DB初期化処理開始");
		Order order = new Order();
		order.setUserId(100);
		order.setStatus(0);
		order.setTotalPrice(0);
		order = orderRepository.insert(order);
		orderId = order.getId();
		System.out.println("インサートが完了しました。");
		System.out.println("DB初期化処理終了");
	}

	@Test
	public void testLoad() {
		System.out.println("主キー検索するテスト開始");

		Integer maxId = template.queryForObject("select max(id) from orders;", new MapSqlParameterSource(),
				Integer.class);
		Order resultOrder = orderRepository.load(maxId);

		assertEquals(100, resultOrder.getUserId(), "ユーザーIDが登録されていません");
		assertEquals(0, resultOrder.getStatus(), "ステータスが登録されていません");
		assertEquals(0, resultOrder.getTotalPrice(), "合計金額が登録されていません");

		System.out.println("主キー検索するテスト終了");
	}

	@Test
	public void testFindByUserIdAndStatus() {
		System.out.println("ユーザーIDとステータスで検索するテスト開始");
		Order resultOrder = orderRepository.findByUserIdAndStatus(100, 0);

		assertEquals(100, resultOrder.getUserId(), "ユーザーIDが登録されていません");
		assertEquals(0, resultOrder.getStatus(), "ステータスが登録されていません");
		assertEquals(0, resultOrder.getTotalPrice(), "合計金額が登録されていません");

		System.out.println("メールアドレスとパスワードで検索するテスト終了");
	}

	@AfterEach
	public void tearDownAfterClass() throws Exception {
		MapSqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		template.update("delete from orders where id = :orderId", param);
		System.out.println("入れたデータを削除しました。");
	}

}
