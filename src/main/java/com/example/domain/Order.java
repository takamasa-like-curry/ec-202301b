package com.example.domain;

import java.util.Date;
import java.util.List;

/**
 * 注文情報を表すドメイン.
 * 
 * @author hayashiasuka
 *
 */
public class Order {

	/** 注文ID(自動採番) */
	private Integer id;
	/** ユーザーID */
	private Integer userId;
	/** 状態 */
	private Integer status;
	/** 合計金額 */
	private Integer totalPrice;
	/** 注文日 */
	private Date orderDate;
	/** ユーザー */
	private User user;
	/** 注文商品のリスト */
	private List<OrderItem> orderItemList;

	/**
	 * 注文商品のリストに入っている小計金額の合計を算出する.
	 * 
	 * @return 算出された小計金額
	 */
	private int subTotal() {

		int subTotal = 0;

		for (OrderItem orderItem : orderItemList) {
			subTotal += orderItem.getSubTotal();
		}

		return subTotal;
	}

	/**
	 * 消費税を算出する.
	 * 
	 * @return 算出された消費税
	 */
	public int getTax() {

		int tax = (int) (subTotal() * 0.1);

		return tax;
	}

	/**
	 * 合計金額を算出する.
	 * 
	 * @return 算出された合計金額
	 */
	public int getCalcTotalPrice() {

		int totalPrice = subTotal() + getTax();

		return totalPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + ", user=" + user + ", orderItemList=" + orderItemList + "]";
	}

}
