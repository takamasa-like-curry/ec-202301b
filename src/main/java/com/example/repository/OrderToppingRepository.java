package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * 
 * order_toppingsテーブル操作するリポジトリ.
 * 
 * @author hayashiasuka
 *
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final RowMapper<OrderTopping> ORDERTOPPING_ROW_MAPPER = new BeanPropertyRowMapper<>(
			OrderTopping.class);

	private static final String TABLE_NAME = "order_toppings";

	public OrderTopping insert(OrderTopping orderTopping) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO " + TABLE_NAME);
		insertSql.append(" (topping_id,order_item_id)");
		insertSql.append(" VALUES");
		insertSql.append(" (:toppingId,:orderItemId)");

//		SqlParameterSource param = new BeanPropertySqlParameterSource(OrderTopping.class);
		SqlParameterSource param = new MapSqlParameterSource().addValue("toppingId", orderTopping.getToppingId())
				.addValue("orderItemId", orderTopping.getOrderItemId());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(insertSql.toString(), param, keyHolder, keyColumnNames);

		orderTopping.setId(keyHolder.getKey().intValue());

		return orderTopping;
	}

	// 【6】注文確認画面表示
	/**
	 * 注文商品IDから注文トッピングを取得する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return 検索された注文トッピング
	 */
	public List<OrderTopping> findByOrderItemId(Integer orderItemId) {

		String sql = "SELECT id, topping_id, order_item_id FROM " + TABLE_NAME + " WHERE order_item_id=:orderItemId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);

		List<OrderTopping> orderToppingList = template.query(sql, param, ORDERTOPPING_ROW_MAPPER);

		return orderToppingList;

	}

}
