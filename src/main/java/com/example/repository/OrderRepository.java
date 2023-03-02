package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


import com.example.domain.Order;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author hayashiasuka
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "orders";
	private static final RowMapper<Order> ORDER_ROW_MAPPER = new BeanPropertyRowMapper<>(Order.class);

	//【6】注文確認画面表示
	/**
	 * 主キーで注文情報を取得する.
	 * 
	 * @param id 注文ID
	 * @return 検索された注文情報
	 */
	public Order load(Integer id) {

		String sql = "SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, "
				+ "destination_zipcode, destination_address, destination_tel, delivery_time, payment_method FROM "
				+ TABLE_NAME + " WHERE id=:id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Order order = template.queryForObject(sql, param, ORDER_ROW_MAPPER);

		return order;

	}
}
