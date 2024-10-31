package com.ezen.spring.handler;

import java.util.List;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PagingHandler {
	// 한 페이지에 나오는 페이지네이션 개수
	private int qty;
	// 한 페이지에 나오는 페이지네이션 시작 번호
	private int startPage;
	// 한 페이지에 나오는 페이지네이션 끝 번호
	private int endPage;
	// 이전 다음 버튼의 노출 여부
	private boolean prev;
	private boolean next;
	
	// DB에서 조회해서 가져올 값
	// 전체 페이지 수 (매개변수)
	private int totalCount;
	// 현재 페이지 번호 : pagingVO의 pageNO 사용할 것 (매개변수)
	private PagingVO pgvo;
	// 전체 페이지 수를 기반으로 계산한 마지막 페이지네이션 번호
	private int realEndPage;
	
	// 댓글용
	private List<CommentVO> cmtList;
	
	// 생성자에서 모든 값들이 계산되어 설정되어야함!
	public PagingHandler(int totalCount, PagingVO pgvo) {
		// 사용자 지정
		this.qty = 10;
		// 매개변수로 가져온 값
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		// 페이지네이션 시작/끝 번호 1/10, 11/20
		// pageNo가 1, 2, 5 등 상관없이 1~10 이어야함!
		// 1 2 3 ... 10 까지는 endPage가 10이어야함
		// 11 12 13 ... 20도 마찬가지로 endPage가 20이어야함
		// >> 현재번호/개수를 올림하여 개수를 다시 곱함
		this.endPage = (int)Math.ceil(pgvo.getPageNo()/(double)qty) * qty;
		this.startPage = endPage - (qty-1);
		
		// 실제 마지막 페이지 번호
		// 전체 글 수 11개이고 개수를 10개로 지정했을 때, 11/10=0.1을 올림해 2가 나옴. 2페이지!
		this.realEndPage = (int)Math.ceil(totalCount/(double)pgvo.getQty());
		
		// 이전, 다음 버튼 노출 여부
		this.prev = this.startPage > 1;
		this.next = this.endPage < this.realEndPage;
		
		if(this.endPage > this.realEndPage) {
			this.endPage = this.realEndPage;
		}

	}
	
	// 댓글용 생성자
	public PagingHandler(int totalCount, PagingVO pgvo, List<CommentVO>cmtList) {
		this(totalCount, pgvo);
		this.cmtList = cmtList;
		
		// 내가 필요한 것 realEndPage, totalCount
	}
	
}
