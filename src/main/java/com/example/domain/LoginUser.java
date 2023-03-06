package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * ユーザーのログイン情報を格納するドメイン.
 * 
 * @author hayashiasuka
 *
 */
public class LoginUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	/** 管理者情報 */
	private final User user;

	/**
	 * 通常の管理者情報に加え、認可用ロールを設定する.
	 * 
	 * @param user          ユーザー情報
	 * @param authorityList 権限情報が入ったリスト
	 */
	public LoginUser(User user, Collection<GrantedAuthority> authorityList) {
		super(user.getEmail(), user.getPassword(), authorityList);
		this.user = user;
	}

	/**
	 * 管理者情報を返す.
	 * 
	 * @return 管理者情報
	 */
	public User getUser() {
		return user;
	}

}
