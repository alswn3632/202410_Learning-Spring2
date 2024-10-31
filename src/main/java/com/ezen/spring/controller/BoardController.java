package com.ezen.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
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
	public String list(Model m, PagingVO pgvo) {
		// request.setAttribute() 대신 Model 객체를 사용함!
//		PagingVO pgvo = new PagingVO();
		// 파라미터로 보내준 pageNo와 qty를 받음
		
		List<BoardVO> list = bsv.getList(pgvo);
		
		// 핸들러로 보내줄 매개변수 totalCount, pgvo
		int totalCount = bsv.getTotal(pgvo);
		log.info(">>>> totalCount > {}", totalCount);
		PagingHandler ph = new PagingHandler(totalCount, pgvo);
		
		m.addAttribute("list", list);
		m.addAttribute("ph", ph);
		
		return "/board/list";
	}
	
	// @requestParam("bno") int bno => 전달되는 파라미터가 여러 개일 경우 이름을 명시
	@GetMapping({"/detail", "/modify"})
	public void detail(int bno, Model m, HttpServletRequest request) {
		// detail과 modify 같이 사용, return void로 변경 : 요청 경로로 응답을 그대로 보낼 수 있어 사용 가능
		
		String path = request.getServletPath();
		
		if(path.equals("/board/detail")) {
			int isOk = bsv.readCount(bno);
			log.info(">>>> readCount > {}", (isOk>0? "성공" : "실패"));
		}
		
		BoardVO bvo = bsv.getDetail(bno);
		m.addAttribute("bvo", bvo);
		
	}
	
	@PostMapping("/update")
	public String update(BoardVO bvo) {
		log.info(">>>> insert bvo > {}", bvo);

		int isOk = bsv.update(bvo);
		log.info(">>>> update > {}", (isOk>0? "성공" : "실패"));
		
		// detail.jsp로 이동하는게 아닌 내부 controller의 detail mapping으로 이동
		return "redirect:/board/detail?bno=" + bvo.getBno();
	}
	
	@GetMapping("/delete")
	public String delete(int bno) {
		int isOk = bsv.delete(bno);
		log.info(">>>> delete > {}", (isOk>0? "성공" : "실패"));
		
		return "redirect:/board/list";
	}
	
}
