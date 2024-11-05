package com.ezen.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ezen.spring.dao.UserDAO;
import com.ezen.spring.domain.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthUserService implements UserDetailsService {
	
	// username, password를 가지고 인증용 토큰 생성 => DB에서 확인
	// return은 UserDetails : 인증 객체 리턴
	
	@Autowired
	private UserDAO udao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username : 로그인을 시도하는 email
		UserVO uvo = udao.selectEmail(username);
		if(uvo == null) {
			// 아이디가 없거나 잘못 입력되면! 에러 발생시키기
			throw new UsernameNotFoundException(username);
		}
		uvo.setAuthList(udao.selectAuths(username));
		log.info(">>>> userDetails > {}", uvo);
		
		return new AuthUser(uvo);
	}

}
