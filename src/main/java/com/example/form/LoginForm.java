package com.example.form;

/**
 * ログイン情報を取得するフォーム.
 * 
 * @author sugaharatakamasa
 *
 */
public class LoginForm {

	/** メールアドレス */
	private String email;
	/** パスワード */
	private String password;
	/** 遷移キー */
	private String key;

	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + ", key=" + key + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
