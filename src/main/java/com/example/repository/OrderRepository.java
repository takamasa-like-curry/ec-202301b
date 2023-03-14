package com.example.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

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

	private static final ResultSetExtractor<List<Order>> ORDER_LIST_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new ArrayList<>();
		Integer latestOrderId = null;
		Order order = null;
		List<OrderItem> orderItemList = null;
		Integer latestOrderItemId = null;
		OrderItem orderItem = null;
		List<OrderTopping> orderToppingList = null;
		OrderTopping orderTopping = null;

		while (rs.next()) {

			Integer orderId = rs.getInt("o_id");
			if (orderId != latestOrderId) {
				latestOrderId = orderId;

				order = new Order();
				orderList.add(order);
				order.setId(latestOrderId);
				order.setUserId(rs.getInt("o_user_id"));
				order.setStatus(rs.getInt("o_status"));
				order.setTotalPrice(rs.getInt("o_total_price"));
				order.setDestinationName(rs.getString("o_destination_name"));
				order.setDestinationEmail(rs.getString("o_destination_email"));
				order.setDestinationZipcode(rs.getString("o_destination_zipcode"));
				order.setDestinationAddress(rs.getString("o_destination_address"));
				order.setDestinationTel(rs.getString("o_destination_tel"));
				Timestamp deliveryTime = rs.getTimestamp("o_delivery_time");
				if (deliveryTime == null) {
					order.setDeliveryTime(null);
				} else {
					order.setDeliveryTime(deliveryTime.toLocalDateTime());
				}
				order.setPaymentMethod(rs.getInt("o_payment_method"));
				orderItemList = new ArrayList<>();
				order.setOrderItemList(orderItemList);

			}

			Integer orderItemId = rs.getInt("oi_id");
			if (orderItemId == 0) {
				continue;
			}
			if (orderItemId != latestOrderItemId) {

				latestOrderItemId = orderItemId;

				orderItem = new OrderItem();
				orderItemList.add(orderItem);

				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setOrderId(latestOrderId);
				orderItem.setQuantity(rs.getInt("oi_quantity"));
				if (rs.getString("oi_size") == null) {
					orderItem.setSize(null);
				} else {
					orderItem.setSize(rs.getString("oi_size").toCharArray()[0]); // 無理矢理感がある
				}
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));
				orderItem.setItem(item);
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
			}

			if (rs.getInt("ot_id") == 0) {
				continue;

			}
			orderTopping = new OrderTopping();
			orderToppingList.add(orderTopping);
			orderTopping.setId(rs.getInt("ot_id"));
			orderTopping.setToppingId(rs.getInt("ot_topping_id"));
			orderTopping.setOrderItemId(latestOrderItemId);
			Topping topping = new Topping();
			topping.setId(rs.getInt("t_id"));
			topping.setName(rs.getString("t_name"));
			topping.setPriceM(rs.getInt("t_price_m"));
			topping.setPriceL(rs.getInt("t_price_l"));
			orderTopping.setTopping(topping);

		}

		return orderList;
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

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", order.getUserId())
				.addValue("status", order.getStatus()).addValue("totalPrice", order.getTotalPrice());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		template.update(insertSql.toString(), param, keyHolder, keyColumnNames);
		order.setId(keyHolder.getKey().intValue());

		return order;
	}

	/**
	 * ユーザーIDとステータスからオーダー情報を取得.
	 * 
	 * @param userId ユーザーID
	 * @param status ステータス
	 * @return 該当オーダー
	 */
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

	/**
	 * ユーザーIDを更新する.
	 * 
	 * @param tentativeUserId 更新前のユーザーID
	 * @param userId          更新後のユーザーID
	 */
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

	/**
	 * オーダー情報の更新.
	 * 
	 * @param order 更新する情報が入ったオーダー
	 */
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

	/**
	 * IDで指定したorderを削除.
	 *
	 * @param orderId 削除するorderのID
	 */
	public void deleteOrderByOrderId(Integer orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM " + TABLE_NAME);
		sql.append(" WHERE id = :orderId");

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);

		template.update(sql.toString(), param);

	}

	/**
	 * ユーザーIDとステータスから該当オーダー全情報を取得.
	 * 
	 * @param userId ユーザーID
	 * @param status ステータス
	 * @return 該当オーダー全情報(なければnull)
	 */
	public List<Order> pickUpOrderFindByUserIdAndStatus(Integer userId, Integer status) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" o.id AS o_id,\n" + "o.user_id AS o_user_id,\n" + "o.status AS o_status,\n"
				+ "o.total_price AS o_total_price,\n" + "o.order_date AS o_order_date,\n"
				+ "o.destination_name AS o_destination_name,\n" + "o.destination_email AS o_destination_email,\n"
				+ "o.destination_zipcode AS o_destination_zipcode,\n"
				+ "o.destination_address AS o_destination_address,\n" + "o.destination_tel AS o_destination_tel,\n"
				+ "o.delivery_time AS o_delivery_time,\n" + "o.payment_method AS o_payment_method,");
		sql.append("oi.id AS oi_id,\n" + "oi.item_id AS oi_item_id,\n" + "oi.order_id AS oi_order_id,\n"
				+ "oi.quantity AS oi_quantity,\n" + "oi.size AS oi_size,");
		sql.append("i.id AS i_id,\n" + "i.name AS i_name,\n" + "i.description AS i_description,\n"
				+ "i.price_m AS i_price_m,\n" + "i.price_l AS i_price_l,\n" + "i.image_path AS i_image_path,\n"
				+ "i.deleted AS i_deleted, ");
		sql.append("ot.id AS ot_id,\n" + "ot.topping_id AS ot_topping_id,\n" + "ot.order_item_id AS ot_order_item_id,");
		sql.append("t.id AS t_id,\n" + "t.name AS t_name,\n" + "t.price_m AS t_price_m,\n" + "t.price_l AS t_price_l");
		sql.append(" FROM orders AS o");
		sql.append(" LEFT OUTER JOIN order_items AS oi");
		sql.append(" ON o.id = oi.order_id");
		sql.append(" LEFT OUTER JOIN items AS i");
		sql.append(" ON oi.item_id = i.id");
		sql.append(" LEFT OUTER JOIN order_toppings AS ot");
		sql.append(" ON oi.id = ot.order_item_id");
		sql.append(" LEFT OUTER JOIN toppings AS t");
		sql.append(" ON ot.topping_id = t.id");
		sql.append(" WHERE");
		sql.append(" o.user_id = :userId");
		sql.append(" AND");
		sql.append(" o.status = :status");
		sql.append(" GROUP BY o.id,oi.id,i.id,ot.id,t.id");
		sql.append(" ORDER BY o.id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);

		List<Order> orderList = template.query(sql.toString().toString(), param, ORDER_LIST_RESULT_SET_EXTRACTOR);
		if (orderList.size() == 0) {
			return null;
		}

		return orderList;
	}
}
