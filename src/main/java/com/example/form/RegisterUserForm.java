package com.example.form;

/**
 * ユーザー登録の際に使用されるフォーム.
 * 
 * @author hayashiasuka
 *
 */
public class RegisterUserForm {

	/** 姓 */
	private String lastName;
	/** 名 */
	private String firstName;
	/** メールアドレス */
	private String email;
	/** 郵便番号 */
	private String zipcode;
	/** 住所 */
	private String address;
	/** 電話番号 */
	private String telephone;
	/** パスワード */
	private String password;
	/** 確認用パスワード */
	private String confirmationPassword;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	@Override
	public String toString() {
		return "RegisterUserForm [lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", zipcode="
				+ zipcode + ", address=" + address + ", telephone=" + telephone + ", password=" + password
				+ ", confirmationPassword=" + confirmationPassword + "]";
	}
}
