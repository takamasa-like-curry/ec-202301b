package com.example.domain;

import java.util.List;

/**
 * 注文商品に関する情報を表すドメイン.
 * 
 * @author hayashiasuka
 *
 */
public class OrderItem {

	/** 注文商品ID(自動採番) */
	private Integer id;
	/** 商品ID */
	private Integer itemId;
	/** 注文ID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** 注文トッピングのリスト */
	private List<OrderTopping> orderToppingList;
	
	/**
	 * 小計を算出するメソッド.
	 * 
	 * @return 小計金額
	 */
	public int getSubTotal() {
		
		int toppingTotalPrice = 0;
		int itemTotalPrice = 0;
		
		if(size == 'M') {
			itemTotalPrice = item.getPriceM() * this.quantity;
			for(OrderTopping orderTopping:orderToppingList) {
				toppingTotalPrice += orderTopping.getTopping().getPriceM();
			}
		} else if(size == 'L') {
			itemTotalPrice = item.getPriceL() * this.quantity;
			for(OrderTopping orderTopping:orderToppingList) {
				toppingTotalPrice += orderTopping.getTopping().getPriceL();
			} 
		} else {
			throw new RuntimeException("例外が発生しました");
		}

		return itemTotalPrice + toppingTotalPrice;
	}
 
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

}
