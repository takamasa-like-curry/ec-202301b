package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowDetailService;

@Controller
@RequestMapping("")
public class ShowDetailCotroller {
	@Autowired
	private ShowDetailService showDetailService;
	
	@GetMapping("/showDetail")
	public String showDetail(Model model,Integer id) {
		Item item=showDetailService.showDetailItem(id);
		model.addAttribute("item",item);
		return "item_detail";
	}
}
