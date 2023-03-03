package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 注文情報を受け取るフォーム.
 * 
 * @author sugaharatakamasa
 *
 */
public class OrderForm {

	/** 宛先氏名 */
	@NotBlank(message = "お名前を入力してください")
	@Size(max = 100, message = "文字数制限を超えています。100文字以下で入力してください")
	private String destinationName;
	/** 宛先メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式で入力してください")
	@Size(max = 100, message = "文字数制限を超えています。100文字以下で入力してください")
	private String destinationEmail;
	/** 宛先郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String destinationZipcode;
	/** 宛先住所 */
	@NotBlank(message = "住所を入力してください")
	@Size(max = 200, message = "文字数制限を超えています。200文字以下で入力してください")
	private String destinationAddress;
	/** 宛先電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "^[0-9]{2,4}-[0-9]{2,4}-[0-9]{2,4}$", message = "電話番号はXXXX-XXXX-XXXXの形式で入力してください")
	private String destinationTel;
	/** 配達日時 */
	@NotBlank(message = "配達日時を入力してください")
	private String deliveryDate;
	/** 配達時間 */
	@NotNull(message = "配達時間を選択してください")
	private Integer deliveryTime;
	/** 支払い方法 */
	@NotNull(message = "お支払い方法を選択してください")
	private Integer paymentMethod;
	/** 合計金額 */
	private Integer totalPrice;
	private Integer orderId;
	private String key;

	@Override
	public String toString() {
		return "OrderForm [destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", deliveryDate=" + deliveryDate + ", deliveryTime="
				+ deliveryTime + ", paymentMethod=" + paymentMethod + ", totalPrice=" + totalPrice + ", orderId="
				+ orderId + ", key=" + key + "]";
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
