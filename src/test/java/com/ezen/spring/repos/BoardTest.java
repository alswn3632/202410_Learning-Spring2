package com.ezen.spring.repos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ezen.spring.dao.BoardDAO;
import com.ezen.spring.domain.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.ezen.spring.config.RootConfig.class})
public class BoardTest {
	
	@Autowired
	private BoardDAO bdao;
	
	@Test
	public void insertBoardDummies() {
		for(int i=0; i<5000; i++) {
			BoardVO bvo = new BoardVO();
			bvo.setTitle("Test Title" + i);
			bvo.setWriter("Tester" + ((int)(Math.random()*500)+1) + "@tester.com");
			bvo.setContent("Test Content" + i);
			
			bdao.insert(bvo);
		}
	}
	
	
}
