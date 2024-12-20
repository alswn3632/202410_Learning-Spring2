package com.ezen.spring.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class, /*추가*/SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}

	// encoding filter 설정
	@Override
	protected Filter[] getServletFilters() {
		// TODO Auto-generated method stub
		CharacterEncodingFilter encoding = new CharacterEncodingFilter();
		// 1. 생성자로 한번에 쓸 수 있음!		
		// CharacterEncodingFilter encoding = new CharacterEncodingFilter("UTF-8",true);
		// 2. 따로 setter를 사용해도 됨!
		encoding.setEncoding("UTF-8");
		encoding.setForceEncoding(true); // 외부로 나가는 데이터 인코딩 여부 true
		
		return new Filter[] {encoding};
	}

	// 사용자 지정 설정이 필요한 경우 사용 (파일 업로드)
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// 사용자 지정 Exception 처리 설정
		registration.setInitParameter("throwExceptionIfNoHandlerFound" , "true");
		
		
		// 파일 업로드 설정 (D:\_myproject\_java\_fileUpload)
		// 위치 설정
		String uploadLocation = "D:\\_myproject\\_java\\_fileUpload";
		int maxFileSize = 1024 * 1024 * 20; // 20MB
		int maxRequest = maxFileSize * 3;
		int fileSizeThreshold = maxFileSize;
		
		MultipartConfigElement multipartConfig = 
				new MultipartConfigElement(uploadLocation, maxFileSize, maxRequest, fileSizeThreshold);
		
		registration.setMultipartConfig(multipartConfig);
	}
	
}
