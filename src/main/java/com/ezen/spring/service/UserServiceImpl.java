package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.spring.dao.UserDAO;
import com.ezen.spring.domain.AuthVO;
import com.ezen.spring.domain.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
	
	private final UserDAO udao;
	
	@Transactional
	@Override
	public int register(UserVO uvo) {
		// TODO Auto-generated method stub
		int isOk = udao.register(uvo);
		return udao.registerAuthInit(uvo.getEmail());
	}

	@Override
	public List<UserVO> getList() {
		// TODO Auto-generated method stub
		return udao.getList();
	}

	@Override
	public List<AuthVO> getAuth(String email) {
		// TODO Auto-generated method stub
		return udao.selectAuths(email);
	}

	@Override
	public int modifyPwdEmpty(UserVO uvo) {
		// TODO Auto-generated method stub
		return udao.modifyPwdEmpty(uvo);
	}

	@Override
	public int modify(UserVO uvo) {
		// TODO Auto-generated method stub
		return udao.modify(uvo);
	}

	@Transactional
	@Override
	public int remove(String email) {
		// TODO Auto-generated method stub
		int isOk = udao.removeAuth(email);
		if(isOk>0) {
			isOk *= udao.remove(email);
		}
		return isOk;
	}

}
