package com.ezen.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Controller
public class BoardController {
	/* 생성자 주입시 객체는 final로 생성해야 함! */
	// @RequiredArgsConstructor로 안하고 @Autowired로 만들 수도 있다.
	// 하지만 @RequiredArgsConstructor가 더 많이 사용함.
	// why? 객체 불변성 확보, 순환 참조 에러 방지
	
	private final BoardService bsv;
	
	// return void : 온 경로 그대로 리턴하게 된다. /board/register => /board/register.jsp
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/insert")
	public String insert(BoardVO bvo) {
		log.info(">>>> insert bvo > {}", bvo);
		int isOk = bsv.insert(bvo);
		log.info(">>>> insert > {}", (isOk>0? "성공" : "실패"));
		
		// return "/" > /WEB-INF/view/.jsp로 경로가 지어지므로 잘못된 방법
		// 컨트롤러의 mapping 위치로 연결할 때는 redirect:/board/list
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public String list(Model m) {
		// request.setAttribute() 대신 Model 객체를 사용함!
		List<BoardVO> list = bsv.getList();
		m.addAttribute("list", list);
		
		return "/board/list";
	}
}
