package com.ezen.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezen.spring.security.CustomAuthUserService;
import com.ezen.spring.security.LoginFailureHandler;
import com.ezen.spring.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@Configuration // WebSecurityConfigurerAdapter 자체로 Config 역할은 받지 못함
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// **비밀번호 암호화 객체 => PasswordEncoder Bean 생성 (스프링에서 제공)
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// **SuccessHandler 객체 생성 => 사용자 커스텀 객체, Bean 생성
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}
	
	// **FailureHandler 객체 생성 => 사용자 커스텀 객체, Bean 생성
	@Bean
	public AuthenticationFailureHandler authFailerHandler() {
		return new LoginFailureHandler();
	}
	
	// **UserDetail 객체 생성, Bean 생성 => 사용자 커스텀 객체
	@Bean
	public UserDetailsService customDetailsService() {
		return new CustomAuthUserService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 인증용 객체를 생성 매니저 설정
		auth.userDetailsService(customDetailsService()).passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 화면에서 설정되는 권한에 따른 주소 맵핑 설정
		// 화면에서 오는 로그인 정보 구성
		// csrf() : 공격에 대한 설정 풀기
		http.csrf().disable();
		
		// 권한 승인 요청 
		// antMatchers : 접근을 허용하는 경로
		// permitAll : 누구 나 접근 가능한 경로 (메인, 회원가입 페이지 등)
		// hasRole('권한') : 해당 권한 확인
		//  - USER, ADMIN, MANAGER
		// authenticated() : 인증된 사용자만 가능한 경로 (권한과 상관없이)
		http.authorizeRequests()
			.antMatchers("/user/list").hasRole("ADMIN")
			.antMatchers("/","/user/login","/user/register","/board/list","/board/detail",
					"/upload/**","/comment/**","/resources/**").permitAll()
			.anyRequest().authenticated();
		
		// 로그인 페이지 구성 : id => email / pw => pwd
		// Controller에 주소 요청 매칭은 같이 있어야 함 (=> 필수) // 실패 루트일 때 controller를 탈 수 있어서
		http.formLogin()
			.usernameParameter("email")
			.passwordParameter("pwd")
			.loginPage("/user/login")
//			.defaultSuccessUrl("/")
//			.failureUrl("/");
			.successHandler(authSuccessHandler())
			.failureHandler(authFailerHandler());
			
		
		// 로그아웃 구성 : method = "post" (?)
		http.logout()
			.logoutUrl("/user/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/");	
	}
	
}
