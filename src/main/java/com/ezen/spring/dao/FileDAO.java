package com.ezen.spring.dao;

import java.util.List;

import com.ezen.spring.domain.FileVO;

public interface FileDAO {

	int insertFile(FileVO fvo);

	List<FileVO> getList(int bno);

	int deleteFile(String uuid);

	List<FileVO> selectListAllFile();

	int countDown(String uuid);

	int countUp(long bno);

}
