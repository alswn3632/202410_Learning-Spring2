package com.ezen.spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment/*")
@RestController
public class CommentController {
	
	private final CommentService csv;
	
	@PostMapping("/post")
	public ResponseEntity<String> post(@RequestBody CommentVO cvo) {
		log.info(">>>> post cvo > {}", cvo);
		int isOk = csv.post(cvo);
		
		if(isOk>0) {
			return new ResponseEntity<String>("1", HttpStatus.OK);			
		}else {
			return new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping(value="/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<CommentVO>> list(@PathVariable("bno") long bno){
//		List<CommentVO> list = csv.getList(bno);
//		return new ResponseEntity<List<CommentVO>>(list, HttpStatus.OK);
//	}
	
	@GetMapping(value="/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> list(@PathVariable("bno") long bno, @PathVariable("page") int page){
		PagingVO pgvo = new PagingVO(page, 5); //DB에 전달할 값을 설정 limit 0, x
		PagingHandler ph = csv.getList(bno, pgvo);
		return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
	}
	
//	@PutMapping("/modify")
//	public ResponseEntity<String> update(@RequestBody CommentVO cvo){
//		
//		return new ResponseEntity<String>();
//	}
	
	@ResponseBody
	@PutMapping("/modify")
	public String modify(@RequestBody CommentVO cvo) {
		int isOk = csv.modify(cvo);
		return isOk>0? "1" : "0";
	}
	
	@ResponseBody
	@DeleteMapping(value="/{cno}")
	public String delete(@PathVariable("cno") long cno) {
		int isOk = csv.delete(cno);
		return isOk>0? "1" : "0";
	}
	
	
}
