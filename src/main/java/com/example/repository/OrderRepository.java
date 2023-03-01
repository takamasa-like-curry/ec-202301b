package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
 * ordersテーブルを操作するクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "orders";
	
	private static final RowMapper<Order> ORDER_ROW_MAPPER = new BeanPropertyRowMapper<>(Order.class) ;

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
		insertSql.append(" (:userId,:status,:total_price)");

		SqlParameterSource param = new BeanPropertySqlParameterSource(Order.class);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };
		int id = template.update(insertSql.toString(), param);
		order.setId(id);

		return order;
	}
	
	public Order findByUserIdAndStatus(Integer userId,Integer status) {
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
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		
		List<Order> orderList = 
		

//		  , destination_tel varchar(15)
//		  , delivery_time timestamp
//		  , payment_method integer
	}
	
	

}
