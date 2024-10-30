package com.ezen.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Setter
@Getter
public class PagingVO {
	//멤버변수
	// 페이지네이션 번호
	private int PageNo;
	// 한 페이지에 표시되는 페이지 수
	private int qty;
	
	// search 변수 포함
	private String type;
	private String keyword;
	
	public PagingVO() {
		// 기본값을 기본생성자로 부여한다.
		// 따라서 @NoArgsConstructor를 쓰지 않고 직접 만들어줌
		// why? 첫 리스트 화면이 나타날 때 기본값을 넣어주기 위해서!
		this.PageNo = 1;
		this.qty = 10;
	}
	
	public PagingVO(int pageNo, int qty) {
		this.PageNo = pageNo;
		this.qty = qty;
	}
	
	// DB에서 사용될 시작 번지 구하기
	// select * from board limit 번지, 개수
	// 1page limit 0, 10 / 2page limit 10, 10 / 3page limit 20, 10
	public int getPageStart() {
		return (this.PageNo-1)*this.qty;
	}
	
	// type의 복합 타입을 각각 검색하기 위해 배열로 생성
	// tyep == twc 라면? [t, w, c]
	public String[] getTypeToArray() {
		return this.type == null? new String[] {} : this.type.split("");
	}
	
	
}
