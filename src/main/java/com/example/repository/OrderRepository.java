package com.example.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author hayashiasuka
 *
 * 
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "orders";

	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		Timestamp deliveryTime = rs.getTimestamp("delivery_time");
		if (deliveryTime == null) {
			order.setDeliveryTime(null);
		} else {
			order.setDeliveryTime(deliveryTime.toLocalDateTime());
		}
		order.setPaymentMethod(rs.getInt("payment_method"));

		return order;
	};

	/**
	 * インサートする.
	 *
	 * @param order インサートするオーダー(注文)情報
	 * @return IDを追加したオーダー(注文)情報
	 */
	public Order insert(Order order) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO " + TABLE_NAME);
		insertSql.append(" (user_id,status,total_price)");
		insertSql.append(" VALUES");
		insertSql.append(" (:userId,:status,:totalPrice)");

		SqlParameterSource param = new MapSqlParameterSource("userId", order.getUserId())
				.addValue("status", order.getStatus()).addValue("totalPrice", order.getTotalPrice());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(insertSql.toString(), param, keyHolder, keyColumnNames);
		order.setId(keyHolder.getKey().intValue());

		return order;
	}

	public Order findByUserIdAndStatus(Integer userId, Integer status) {

		StringBuilder findByUserIdAndStatusSql = new StringBuilder();
		findByUserIdAndStatusSql.append("SELECT ");
		findByUserIdAndStatusSql.append(" id,");
		findByUserIdAndStatusSql.append("user_id,");
		findByUserIdAndStatusSql.append("status,");
		findByUserIdAndStatusSql.append("total_price,");
		findByUserIdAndStatusSql.append("order_date,");
		findByUserIdAndStatusSql.append("destination_name,");
		findByUserIdAndStatusSql.append("destination_email,");
		findByUserIdAndStatusSql.append("destination_zipcode,");
		findByUserIdAndStatusSql.append("destination_address,");
		findByUserIdAndStatusSql.append("destination_tel,");
		findByUserIdAndStatusSql.append("delivery_time,");
		findByUserIdAndStatusSql.append("payment_method");
		findByUserIdAndStatusSql.append(" FROM " + TABLE_NAME);
		findByUserIdAndStatusSql.append(" WHERE");
		findByUserIdAndStatusSql.append(" user_id = :userId");
		findByUserIdAndStatusSql.append(" AND");
		findByUserIdAndStatusSql.append(" status = :status");
		findByUserIdAndStatusSql.append(" ORDER BY id DESC");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);

		List<Order> orderList = template.query(findByUserIdAndStatusSql.toString(), param, ORDER_ROW_MAPPER);

		if (orderList.size() == 0) {
			return null;
		}

		return orderList.get(0);
	}

	// 【6】注文確認画面表示
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

	public void updateUserId(Integer tentativeUserId, Integer userId) {
		StringBuilder updateUserIdSql = new StringBuilder();
		updateUserIdSql.append("UPDATE " + TABLE_NAME);
		updateUserIdSql.append(" SET");
		updateUserIdSql.append(" user_id = :userId");
		updateUserIdSql.append(" WHERE user_id = :tentativeUserId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("tentativeUserId",
				tentativeUserId);

		template.update(updateUserIdSql.toString(), param);

	}

	public void update(Order order) {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("UPDATE " + TABLE_NAME);
		updateSql.append(" SET");
		updateSql.append(" user_id = :userId,");
		updateSql.append(" status = :status,");
		updateSql.append(" total_price = :totalPrice,");
		updateSql.append("order_date = :orderDate,");
		updateSql.append("destination_name = :destinationName,");
		updateSql.append("destination_email = :destinationEmail,");
		updateSql.append("destination_zipcode = :destinationZipcode,");
		updateSql.append("destination_address = :destinationAddress,");
		updateSql.append("destination_tel = :destinationTel,");
		updateSql.append("delivery_time = :deliveryTime,");
		updateSql.append("payment_method = :paymentMethod");
		updateSql.append(" WHERE id = :id");

		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		template.update(updateSql.toString(), param);
	}
	
	/**
	 * ユーザーIDで注文情報を取得する.
	 * 
	 * @param userId ユーザーID
	 * @return 検索された注文情報
	 */
	public List<Order> findByUserId(Integer userId) {

		StringBuilder findByUserIdAndStatusSql = new StringBuilder();
		findByUserIdAndStatusSql.append("SELECT ");
		findByUserIdAndStatusSql.append(" id,");
		findByUserIdAndStatusSql.append("user_id,");
		findByUserIdAndStatusSql.append("status,");
		findByUserIdAndStatusSql.append("total_price,");
		findByUserIdAndStatusSql.append("order_date,");
		findByUserIdAndStatusSql.append("destination_name,");
		findByUserIdAndStatusSql.append("destination_email,");
		findByUserIdAndStatusSql.append("destination_zipcode,");
		findByUserIdAndStatusSql.append("destination_address,");
		findByUserIdAndStatusSql.append("destination_tel,");
		findByUserIdAndStatusSql.append("delivery_time,");
		findByUserIdAndStatusSql.append("payment_method");
		findByUserIdAndStatusSql.append(" FROM " + TABLE_NAME);
		findByUserIdAndStatusSql.append(" WHERE");
		findByUserIdAndStatusSql.append(" user_id = :userId");
		findByUserIdAndStatusSql.append(" AND");
		findByUserIdAndStatusSql.append(" status != 0");
		findByUserIdAndStatusSql.append(" ORDER BY order_date DESC");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);

		List<Order> orderList = template.query(findByUserIdAndStatusSql.toString(), param, ORDER_ROW_MAPPER);

		return orderList;
	}

	public void deleteByOrderId(Integer orderId) {
		StringBuilder deleteByOrderId = new StringBuilder();
		deleteByOrderId.append("DELETE FROM " + TABLE_NAME);
		deleteByOrderId.append(" WHERE id = :orderId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);

		template.update(deleteByOrderId.toString(), param);

	}

}
