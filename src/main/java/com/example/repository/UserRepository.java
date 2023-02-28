package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * usersテーブルを操作するクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLE_NAME = "users";
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipCode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));

		return user;
	};

	public User findByEmailAndPassword(String email, String password) {
		StringBuilder findByEmailAndPasswordSql = new StringBuilder();
		findByEmailAndPasswordSql.append("SELECT");
		findByEmailAndPasswordSql.append(" id,");
		findByEmailAndPasswordSql.append("name,");
		findByEmailAndPasswordSql.append("email,");
		findByEmailAndPasswordSql.append("password,");
		findByEmailAndPasswordSql.append("zipcode,");
		findByEmailAndPasswordSql.append("address,");
		findByEmailAndPasswordSql.append("telephone");
		findByEmailAndPasswordSql.append(" FROM " + TABLE_NAME);
		findByEmailAndPasswordSql.append(" WHERE");
		findByEmailAndPasswordSql.append(" email = :email");
		findByEmailAndPasswordSql.append(" AND");
		findByEmailAndPasswordSql.append(" password = :password;");
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		
		List<User> userList = template.query(findByEmailAndPasswordSql.toString(),param,USER_ROW_MAPPER);
		if(userList.size() == 0) {
			return null;
		}
		
		return userList.get(0);

	}
}
