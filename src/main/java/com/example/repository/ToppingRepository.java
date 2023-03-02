package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Topping;

@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "toppings";

	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = new BeanPropertyRowMapper<>(Topping.class);

	public Topping load(Integer id) {
		StringBuilder loadSql = new StringBuilder();
		loadSql.append("SELECT");
		loadSql.append(" id,name,price_m,price_l");
		loadSql.append(" FROM " + TABLE_NAME);
		loadSql.append(" WHERE");
		loadSql.append(" id =  :id");

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Topping topping = template.queryForObject(loadSql.toString(), param, TOPPING_ROW_MAPPER);

		return topping;

	}

}
