package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.spring.dao.BoardDAO;
import com.ezen.spring.dao.FileDAO;
import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;
	
	private final FileDAO fdao;

//	@Override
//	public int insert(BoardVO bvo) {
//		// TODO Auto-generated method stub
//		return bdao.insert(bvo);
//	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return bdao.getList(pgvo);
	}

//	@Override
//	public BoardVO getDetail(int bno) {
//		// TODO Auto-generated method stub
//		return bdao.getDetail(bno);
//	}
	
	@Transactional
	@Override
	public BoardDTO getDetail(int bno) {
		// bdao > bno , fdao > fvo 받아서 합쳐서 보내야함
		BoardVO bvo = bdao.getDetail(bno);
		
		List<FileVO> flist = fdao.getList(bno);
		
		BoardDTO bdto = new BoardDTO(bvo, flist);
		return bdto;
	}

//	@Override
//	public int update(BoardVO bvo) {
//		// TODO Auto-generated method stub
//		return bdao.update(bvo);
//	}

	@Override
	public int delete(int bno) {
		// TODO Auto-generated method stub
		return bdao.delete(bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return bdao.getTotal(pgvo);
	}

	@Override
	public int readCount(int bno) {
		return bdao.readCount(bno);
	}

	@Transactional // insert가 실행되는 동안은 구문이 완전히 적용되지 않음 
	// => insert하고 bvo를 넣고 bno를 가져올 동안 다른 동작이 테이블에 접근할 수 없음
	// => 안전하게 bno를 가져올 수 있게 된다.
	@Override
	public int insert(BoardDTO bdto) {
		// bvo + file 
		// bvo 먼저 insert하고난 후 bno를 DB에서 가져와야함! > fvo를 DB에 저장
		
		// 1. bvo 넣기
		int isOk = bdao.insert(bdto.getBvo());
		
		// 2. 방금 넣은 bvo에서 bno 가져오기
		if(bdto.getFlist() == null) {
			// 첨부파일이 없다면? 여기서 끝
			return isOk;
		}
		
		// 3. bno 가지고 file 등록
		if(isOk>0 && bdto.getFlist().size() > 0) {
			// bvo 잘 들어갔고, flist도 잘 갖춰져있다면?
			long bno = bdao.getOneBno(); // 가장 마지막에 저장된 튜플의 bno
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				// 하나라도 실패하면 롤백
				isOk *= fdao.insertFile(fvo);
			}
			// 4. 등록한 파일 개수만큼 업데이트
			isOk *= fdao.countUp(bdto.getBvo().getBno());
		}
		
		return isOk;
	}

	@Override
	public int deleteFile(String uuid) {
		// 삭제하기 전 file 테이블에서 uuid가 포함된 튜플의 bno를 가져와 일치하는 board 테이블의 튜플을 -1
		int isOk = fdao.countDown(uuid);
		if(isOk>0) {
			isOk *= fdao.deleteFile(uuid);
		}
		return isOk;
	}

	@Transactional
	@Override
	public int update(BoardDTO boardDTO) {
		// insert와 달리 bno가 이미 존재함
		int isOk = bdao.update(boardDTO.getBvo());
		if(boardDTO.getFlist() == null) {
			return isOk;
		}
		
		if(isOk>0 && boardDTO.getFlist().size()>0) {
			for(FileVO fvo : boardDTO.getFlist()) {
				fvo.setBno(boardDTO.getBvo().getBno());
				isOk *= fdao.insertFile(fvo);
			}
			isOk *= fdao.countUp(boardDTO.getBvo().getBno());
		}
		return isOk;
	}
}
