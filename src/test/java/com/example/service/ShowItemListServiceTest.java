package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

@SpringBootTest
public class ShowItemListServiceTest {

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ShowItemListService showItemListService;

	@BeforeAll
	public static void beforeAll() {
		System.out.println("テストを開始します。");
	}

	@Test
	public void showItemListTest1() {
		System.out.println("商品一覧表示のテストのテストを開始します(name == nullの場合)");
		showItemListService.showItemList(null);

		Mockito.verify(itemRepository).findAll();
		System.out.println("テストを終了します。");

	}

	@Test
	public void showItemListTest2() {
		System.out.println("商品一覧表示のテストのテストを開始します(name == nameの場合)");

		showItemListService.showItemList("name");
		System.out.println("成功");
		Mockito.verify(itemRepository, times(1)).findByName("name");
		System.out.println("テストを終了します。");

	}

	@Test
	public void showItemListTest3() {
		System.out.println("商品一覧表示のテストのテストを開始します(whenを使用)");
		List<Item> itemList = new ArrayList<>();
		int num = 10;
		for (int i = 0; i < num; i++) {
			itemList.add(new Item());
		}
//		Mockito.when(itemRepository.findAll()).thenReturn(new ArrayList<Item>());
		Mockito.when(itemRepository.findByName("name")).thenReturn(itemList);

		List<Item> resultItemList = showItemListService.showItemList("name");
		assertEquals(resultItemList.size(), num, "取得したリストに問題があります。");
		assertEquals(resultItemList.get(0).getId(), null, "取得したリストに問題があります。");

	}
}
