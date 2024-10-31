package com.ezen.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class CommentVO {
	//멤버변수
	private long cno;
	private long bno;
	private String writer;
	private String content;
	private String regDate;
}
