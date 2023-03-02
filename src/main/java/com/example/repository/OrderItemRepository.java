package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * order_itemテーブルを操作すクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "order_items";

	public OrderItem insert(OrderItem orderItem) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO " + TABLE_NAME);
		insertSql.append(" (item_id,order_id,quantity,size)");
		insertSql.append(" VALUES");
		insertSql.append(" (:itemId,:orderId,:quantity,:size)");

		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", orderItem.getItemId())
				.addValue("orderId", orderItem.getOrderId()).addValue("quantity", orderItem.getQuantity())
				.addValue("size", orderItem.getSize());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(insertSql.toString(), param, keyHolder, keyColumnNames);
		orderItem.setId(keyHolder.getKey().intValue());

		return orderItem;
	}

	public void delete(Integer deleteItemId) {
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("DELETE FROM " + TABLE_NAME);
		deleteSql.append(" WHERE id = :deleteItemId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("deleteItemId", deleteItemId);

		template.update(deleteSql.toString(), param);
	}
}
