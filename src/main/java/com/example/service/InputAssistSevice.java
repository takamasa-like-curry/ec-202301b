package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザー情報送信APIの業務処理を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Service
@Transactional
public class InputAssistSevice {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザーID検索からユーザーを取得
	 * 
	 * @param userId ユーザーID
	 * @return 該当ユーザー情報
	 */
	public User load(Integer userId) {
		return userRepository.load(userId);

	}
}
