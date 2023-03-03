package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	private static final String TABLE_NAME = "items";

	/**
	 * Item オブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

	/**
	 * 商品に一覧を表示します
	 * 
	 * @return 一覧情報
	 */
	public List<Item> findAll() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted From items ORDER BY price_m ASC;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品の名前を曖昧検索します.
	 * 
	 * @param name
	 * @return 曖昧検索後の一覧情報
	 */
	public List<Item> findByName(String name) {

		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE name ILIKE :name ORDER BY price_m ASC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	public Item load(Integer id) {
		StringBuilder loadByIdSql = new StringBuilder();
		loadByIdSql.append("SELECT");
		loadByIdSql.append(" id,name,description,price_m,price_l,image_path,deleted");
		loadByIdSql.append(" FROM " + TABLE_NAME);
		loadByIdSql.append(" WHERE id = :id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Item item = template.queryForObject(loadByIdSql.toString(), param, ITEM_ROW_MAPPER);

		return item;

	}

}
