package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

	// 【1】ユーザー登録をする
	/**
	 * Eメールからユーザー情報を検索する.
	 * 
	 * @param email Eメール
	 * @return 検索されたユーザー情報
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone FROM " + TABLE_NAME
				+ " WHERE email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}

		return userList.get(0);
	}

	/**
	 * ユーザー登録を実施する.
	 * 
	 * @param registerUserForm フォーム
	 */
	public void insert(User user) {
		String sql = "INSERT INTO " + TABLE_NAME
				+ " (name, email, password, zipcode, address, telephone) VALUES(:name, :email, :password, :zipcode, :address, :telephone);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	// 【2】ログインをする
	/**
	 * メールアドレスとパスワードからユーザ情報を検索する.
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return 検索されたユーザー情報
	 */
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

		List<User> userList = template.query(findByEmailAndPasswordSql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}

		return userList.get(0);

	}
}
