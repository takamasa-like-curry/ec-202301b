package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * order_itemsテーブルを操作するリポジトリ.
 * 
 * @author hayashiasuka
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "order_items";
	private static final RowMapper<OrderItem> ORDERITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderItem.class);

	//【6】注文確認画面表示
	/**
	 * 注文IDから注文商品を取得する.
	 * 
	 * @param orderId 注文ID
	 * @return 検索された注文商品
	 */
	public List<OrderItem> findByOrderId(Integer orderId) {

		String sql = "SELECT id, item_id, order_id, quantity, size FROM " + TABLE_NAME + " WHERE order_id=:orderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);

		List<OrderItem> orderItemList = template.query(sql, param, ORDERITEM_ROW_MAPPER);

		return orderItemList;
	}
}
