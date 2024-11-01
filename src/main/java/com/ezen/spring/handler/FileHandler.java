package com.ezen.spring.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.spring.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component // 사용자 클래스 Bean으로 등록하는 어노테이션
public class FileHandler {
	// 저장경로
	private final String UP_DIR = "D:\\_myproject\\_java\\_fileUpload";
	
	// 첨부 파일을 주고 FileVO 리스트로 변환하여 리턴 + 저장
	public List<FileVO> uploadFiles(MultipartFile[] files) {
		
		List<FileVO> flist = new ArrayList<>();
		
		// fileVO 생성 + 파일 저장 + 썸네일 파일 저장
		// 날짜별로 폴더 생성하여 업로드 파일 관리
		LocalDate date = LocalDate.now(); // 오늘 날짜 리턴 2024-11-01
		log.info(">>>> date > {}", date.toString());
		String today = date.toString();
		today = today.replace("-", File.separator); // 2024\\11\\01
		
		// D:\\_myproject\\_java\\_fileUpload\\2024\\11\\01
		File folders = new File(UP_DIR, today);
		
		// 폴더 생성 : mkdir (1개만 생성) mkdirs(하위폴더까지 생성)
		// 이미 있다면 만들지 않음, 없을 때만 생성
		if(!folders.exists()) {
			folders.mkdirs();
		}
		
		// files를 가지고 flist 생성
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSaveDir(today); //날짜 경로를 줌
			fvo.setFileSize(file.getSize());
//			String fileName  = file.getOriginalFilename();
			// 경로를 포함하는 이름이라면
			String fileName = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf(File.separator)+1);
			fvo.setFileName(fileName);
			
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			fvo.setUuid(uuidStr);
			
			// -----fvo 생성완료----- 남은거 bno / file_type(File 객체로 전달해야해서 저장 후 확인)
			// 디스트에 저장 => 저장할 객체(File)를 생성해서 저장
			String fullFileName = uuidStr + "_" + fileName;
			File storeFile = new File(folders, fullFileName);
			
			try {
				file.transferTo(storeFile); // 저장 내부저장은 write 이건 외부로 보낸다는 뜻
				// 썸네일 저장 => 이미지만 가능
				if(isImageFile(storeFile)) {
					fvo.setFileType(1); // 이미지 파일만 1 아닌것은 디폴트로 0 들어갈 것!
					// 썸네일 생성
					File thumbNail = new File(folders, uuidStr + "_th_" + fileName);
					Thumbnails.of(storeFile).size(100, 100).toFile(thumbNail);
				}
				
			} catch (Exception e) {
				log.info(">>>> 파일 저장 오류 발생");
				e.printStackTrace();
			}
			
			// 저장 끝! 이제 배열에 누적!
			flist.add(fvo);
			
		}
		
		return flist;
	}
	
	private boolean isImageFile(File storeFile) throws IOException {
		String mimeType = new Tika().detect(storeFile); // file의 내부요소 type "image/png"을 인지함 
		return mimeType.startsWith("image")? true : false;
	}
	
	
}
