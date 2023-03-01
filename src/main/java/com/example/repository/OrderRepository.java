package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
	
	
	
	
}
