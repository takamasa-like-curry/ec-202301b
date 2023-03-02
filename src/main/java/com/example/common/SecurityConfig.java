package com.example.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * spring securityに関するクラス.
 * 
 * @author hayashiasuka
 *
 */
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// 認可に関する設定
		http.authorizeHttpRequests()
				.requestMatchers("/showList", "/showDetail", "/showCartList", "/registerUser", "/loginAndLogout").permitAll()
				.anyRequest().authenticated();

		// ログインに関する設定
		http.formLogin()
			.loginPage("/loginAndLogout")
			.loginProcessingUrl("/login")
			.failureUrl("/?error=true")
			.defaultSuccessUrl("/showList", false)
			.usernameParameter("email")
			.passwordParameter("password");

		// ログアウトに関する設定
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) 
			.logoutSuccessUrl("/showList") 
			.deleteCookies("JSESSIONID") 
			.invalidateHttpSession(true);

		return http.build();

	}

	/**
	 * アルゴリズムのハッシュ化する実装を行うメソッド.
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

}
