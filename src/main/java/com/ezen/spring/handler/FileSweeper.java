package com.ezen.spring.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ezen.spring.dao.FileDAO;
import com.ezen.spring.domain.FileVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Component
public class FileSweeper {
	
	// 매일 정해진 시간에 스케줄러를 실행
	// 매일 등록된 파일과 (DB) - 해당 일자의 폴더에 있는 파일이 일치하는 파일은 남겨두고 
	// 일치하지 않는 파일이 있다면 삭제! (DB에는 없는데, 폴더에는 남아있는 파일이 있다면 삭제)
	// 실제 삭제는 file.delete() 를 통해 직접 fileRemoveHandler를 사용해 삭제해도 무방함!
	
	// 직접 DB 접속을 해서 처리
	private final FileDAO fdao;
	// 파일이 있는 위치
	private final String BASE_PATH = "D:\\_myproject\\_java\\_fileUpload\\";
	
	// @ : 스케줄기록 cron 방식 => 초, 분, 시, 일, 월, 요일, 년도(생략가능)
	@Scheduled(cron="00 15 13 * * *") // 매일 23시 59분 59초에 실행
	public void fileSweeper() {
		log.info(">>>> FileSweeper Running Start > {}", LocalDateTime.now());
		
		// DB에 등록된 모든 파일 목록을 가져오기
		List<FileVO> dbList = fdao.selectListAllFile();
		// 여기서 나온 file 정보를 이용해 아래의 경로를 만들어 삭제 진행해야함
		// D:\\_myproject\\_java\\_fileUpload\\2024\\11\\04\\uuid_파일이름
		// D:\\_myproject\\_java\\_fileUpload\\2024\\11\\04\\uuid_th_파일이름
		// (1) 파일 경로 + 파일명을 붙인 실제 존재해야하는 파일 리스트
		List<String> currFiles = new ArrayList<String>();
		for(FileVO fvo : dbList) {
			String filePath = fvo.getSaveDir() + File.separator + fvo.getUuid();
			String fileName = fvo.getFileName();
			
			currFiles.add(BASE_PATH + filePath + "_" + fileName);
			// 이미지라면 썸네일 이미지까지 삭제
			if(fvo.getFileType() > 0) {
				currFiles.add(BASE_PATH + filePath + "_th_" + fileName);				
			}
		}
		log.info(">>>> currFiles > {}", currFiles);
		
		// (2) 실제 파일 경로를 설정
		LocalDate now = LocalDate.now();
		String today = now.toString();
		today = today.replace("-", File.separator);
		
		// 경로를 기반으로 저장되어있는 파일을 검색
		File dir = Paths.get(BASE_PATH+today).toFile();
		// dir => D:\\_myproject\\_java\\_fileUpload\\2024\\11\\04
		
		// listFiles() : 경로에 있는 모든 파일을 배열로 리턴
		File[] allFileObj = dir.listFiles();
		log.info(">>>> allFileObj > {}", allFileObj.toString());
		
		// 실제 저장되어 있는 파일 목록(2)과, DB의 존재 파일(1)을 비교하여 DB에 없는 파일은 삭제 진행
		for(File file : allFileObj) {
			String storedFileName = file.toPath().toString();
			
			if(!currFiles.contains(storedFileName)) {
				file.delete(); // 파일 삭제
				log.info(">>>> delete files > {}", storedFileName);
			}
		}
		
		
		
		
		log.info(">>>> FileSweeper Running End > {}", LocalDateTime.now());
	}
	
	
	
	
}
