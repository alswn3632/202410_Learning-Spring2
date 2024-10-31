package com.ezen.spring.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;

public interface CommentDAO {

	int post(CommentVO cvo);

//	List<CommentVO> getList(long bno);

	int modify(CommentVO cvo);

	int delete(long cno);

	List<CommentVO> getList(@Param("bno") long bno, @Param("pgvo") PagingVO pgvo);

	int getTotalCount(long bno);

}
