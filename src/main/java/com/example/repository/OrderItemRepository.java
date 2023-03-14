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

	private static final RowMapper<OrderItem> ORDERITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderItem.class);
	private static final String TABLE_NAME = "order_items";

	/**
	 * orderItemを挿入する.
	 * 
	 * @param orderItem 挿入するorderItem
	 * @return IDを含むorderItem
	 */
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

	/**
	 * orderItemを削除する.
	 * 
	 * @param deleteItemId 削除するorderItem
	 */
	public void delete(Integer deleteItemId) {
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("DELETE FROM " + TABLE_NAME);
		deleteSql.append(" WHERE id = :deleteItemId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("deleteItemId", deleteItemId);

		template.update(deleteSql.toString(), param);
	}

	// 【6】注文確認画面表示
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

	/**
	 * オーダーIDの変更を行う.
	 * 
	 * @param tentativeOrderId 変更するオーダーID
	 * @param orderId          変更後のオーダーID
	 */
	public void updateOrderId(Integer tentativeOrderId, Integer orderId) {
		StringBuilder updateOrderIdSql = new StringBuilder();
		updateOrderIdSql.append("UPDATE " + TABLE_NAME);
		updateOrderIdSql.append(" SET");
		updateOrderIdSql.append(" order_id = :orderId");
		updateOrderIdSql.append(" WHERE");
		updateOrderIdSql.append(" order_id = :tentativeOrderId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId).addValue("tentativeOrderId",
				tentativeOrderId);

		template.update(updateOrderIdSql.toString(), param);
	}

}
