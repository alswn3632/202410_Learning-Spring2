package com.ezen.spring.config;

import org.springframework.context.annotation.ComponentScan;
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
		
		// 나중에 파일 업로드 경로도 추가할 예정
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
	
	// 파일 업로드를 위한 리졸버 추가할 예정
	
}
