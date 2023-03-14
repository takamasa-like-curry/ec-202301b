package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Item;

@SpringBootTest
public class ItemReposirotyTest {

	@Autowired
	private ItemRepository itemRepository;

	@Test
	@Disabled
	public void findAllTest() {

		List<Item> itemList = itemRepository.findAll();
		Integer size = itemList.size();
		assertEquals(itemList.size(), 18, "全件検索で取得したリストのサイズが異なります。");
		assertEquals(itemList.get(0).getId(), 1, "最初のアイテムのIDが異なります。");
		assertEquals(itemList.get(size - 1).getId(), 18, "最後のアイテムのIDが異なります。");
		assertEquals(itemList.get(0).getName(), "カツカレー", "最初のアイテムのIDが異なります。");
		assertEquals(itemList.get(size - 1).getName(), "黄金に輝くチキンカレー", "最後のアイテムのIDが異なります。");

	}

	@Test
	public void loadTest() {
		System.out.println("主キー検索のテストを開始します。");
		Integer id = 10;
		Item resultItem = itemRepository.load(id);
		assertEquals(resultItem.getId(), 10, "主キー検索を取得したitemのIDが異なります。");
		assertEquals(resultItem.getName(), "ほうれん草のカレードリア", "主キー検索を取得したitemの名前が異なります。");
		assertEquals(resultItem.getPriceM(), 2160, "主キー検索を取得したitemのMサイズ料金が異なります。");
		System.out.println("主キー検索テスト問題ありません。");
		System.out.println("主キー検索のテストを終了します。");

	}

}
