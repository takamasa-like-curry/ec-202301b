package com.example.form;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * 追加する商品情報を受け取るフォーム.
 * 
 * @author sugaharatakamasa
 *
 */
public class AddItemForm {

	/** 商品ID */
	private Integer itemId;
	/** サイズ */
	@NotNull(message = "サイズを選択してください")
	private Character size;
	/** トッピングリスト */
	private List<Integer> toppingIdList;
	/** 数量 */
	@NotNull(message = "数量を選択してください")
	private Integer quantity;

	@Override
	public String toString() {
		return "AddItemForm [itemId=" + itemId + ", size=" + size + ", toppingIdList=" + toppingIdList + ", quantity="
				+ quantity + "]";
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public List<Integer> getToppingIdList() {
		return toppingIdList;
	}

	public void setToppingIdList(List<Integer> toppingIdList) {
		this.toppingIdList = toppingIdList;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
