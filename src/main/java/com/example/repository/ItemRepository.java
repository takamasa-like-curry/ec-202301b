package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Item	オブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER=new BeanPropertyRowMapper<>(Item.class);
	
	
	/**
	 * 商品に一覧を表示します
	 * @return　一覧情報
	 */
	public List<Item> findAll(){
		String sql="SELECT id,name,description,price_m,price_l,image_path,deleted From items;";
		List<Item> itemList=template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
}
