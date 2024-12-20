package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.spring.dao.CommentDAO;
import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
	
	private final CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		// TODO Auto-generated method stub
		int isOk = cdao.post(cvo);
		if(isOk>0) {
			isOk *= cdao.countUp(cvo.getBno());
		}
		return isOk;
	}

//	@Override
//	public List<CommentVO> getList(long bno) {
//		// TODO Auto-generated method stub
//		return cdao.getList(bno);
//	}

	@Override
	public int modify(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.modify(cvo);
	}

	@Override
	public int delete(long cno) {
		int isOk = cdao.countDown(cno);
		if(isOk>0) {
			isOk *= cdao.delete(cno);
		}
		return isOk;
	}

	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		// ph 객체 안에 cmtList, TotalCount 구하기
		List<CommentVO> cmtList = cdao.getList(bno, pgvo);
		int totalCount = cdao.getTotalCount(bno);
		PagingHandler ph = new PagingHandler(totalCount, pgvo, cmtList);
		return ph;
	}
}
