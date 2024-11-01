package com.ezen.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan(basePackages = {"com.ezen.spring.controller","com.ezen.spring.service","com.ezen.spring.handler"})
public class ServletConfiguration implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// resources 경로 설정 (css, js, img, font...)
		// 앞의 경로는 핸들러에 지정할 이름, 뒤의 경로는 실제 경로
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		
		// **(추가) 파일 업로드 경로
		// "D:\\_myproject\\_java\\_fileUpload"  경로를 간단하게 줄여줌
		registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:\\_myproject\\_java\\_fileUpload\\");
		
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// view를 JSP(JSTL포함)로 어떻게 보여줄지 설정함
		// 여기서 view의 경로 설정
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		// 화면에 뷰를 구성할 때 jstl에 대한 사용을 하는 JSP를 구성하겠다!
		viewResolver.setViewClass(JstlView.class);
		
		registry.viewResolver(viewResolver);
	}
	
	// **(추가) 파일 업로드를 위한 리졸버
	// Bean 이름이 multipartResolver 여야 에러가 발생하지 않음
	@Bean(name = "multipartResolver")
	public MultipartResolver getMultipartResolver() {
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return multipartResolver;
	}
	
}
