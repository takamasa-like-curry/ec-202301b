package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.repository.UserRepository;

/**
 * ログイン・ログアウトに関する業務を行うクラス.
 * 
 * @author sugaharatakamasa
 *
 */
@Service
@Transactional
public class LoginAndLogoutService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User login(LoginForm form) {
		User user = userRepository.findByEmail(form.getEmail());
		//ハッシュ化されたパスワードとの整合確認
		if(passwordEncoder.matches(form.getPassword(), user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}
}
