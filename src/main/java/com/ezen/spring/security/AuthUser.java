package com.ezen.spring.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ezen.spring.domain.UserVO;

import lombok.Getter;

@Getter
public class AuthUser extends User {

	private static final long serialVersionUID = 1L;
	
	// 얘를 통해서 나중에 uvo를 꺼내서 사용할 수 있게 되는건가...?
	private UserVO uvo;
	
	
	public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public AuthUser(UserVO uvo) {
		super(uvo.getEmail(), uvo.getPwd(), 
				uvo.getAuthList().stream()
				.map(authVO -> new SimpleGrantedAuthority(authVO.getAuth()))
				.collect(Collectors.toList())
				);
		// User가 줄 것
		
		// 내가 만든 것 => 얘를 통해서 화면에서 정보를 사용할 수 있음
		this.uvo = uvo;
	}

}
