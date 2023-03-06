package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.User;
import com.example.service.InputAssistSevice;

@RestController
@RequestMapping("input-assist")
public class InputAssistController {

	@Autowired
	private InputAssistSevice service;

	@GetMapping("")
	public Map<String, User> check(Integer userId) {
		System.out.println(userId);
		Map<String, User> map = new HashMap<>();
		User user = service.load(userId);
		map.put("user", user);
		return map;

	}

}
