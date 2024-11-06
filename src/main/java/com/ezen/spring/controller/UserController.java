package com.ezen.spring.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/**")
@Controller
public class UserController {
	
	private final UserService usv;
	// 암호화
	private final BCryptPasswordEncoder bcEncoder;
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/register")
	public String register(UserVO uvo) {
		log.info(">>>> UserVO uvo > {}", uvo);
		
		// encode : 암호화
		uvo.setPwd(bcEncoder.encode(uvo.getPwd()));
		int isOk = usv.register(uvo);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, RedirectAttributes re/*모델처럼 addAttribute 가능*/) {
		// 실제 로그인은 Security의 필터에서 가져감.
		// 로그인 실패시 다시 로그인 페이지로 돌아와서 오류 메세지를 출력하도록 하거나
		// 재로그인을 유도하기 위한 작업
		log.info(">>>> errMsg > {}", request.getAttribute("errMsg").toString());
		re.addAttribute("email", request.getAttribute("email"));
		re.addAttribute("errMsg", request.getAttribute("errMsg"));
		return "redirect:/user/login";
	}
	
	@GetMapping("/list")
	public void list(Model m) {
		List<UserVO> userList = usv.getList();
		for(UserVO uvo : userList) {
			uvo.setAuthList(usv.getAuth(uvo.getEmail()));
		}
		m.addAttribute("userList", userList);	
	}
	
	@GetMapping("/modify")
	public void modify() {
		
	}
	
	@PostMapping("/modify")
	public String modify(UserVO uvo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes re) {
		log.info(">>>> modify uvo > {}", uvo);
		int isOk;
		if(uvo.getPwd().isEmpty() || uvo.getPwd().length() == 0) {
			isOk = usv.modifyPwdEmpty(uvo);
		}else {
			uvo.setPwd(bcEncoder.encode(uvo.getPwd()));
			isOk = usv.modify(uvo);
		}
		// 회원 정보 수정 후 로그아웃?
		if(isOk>0) {
			re.addFlashAttribute("modify_msg", "ok");			
		}else {
			re.addFlashAttribute("modify_msg", "fail");						
		}
		logout(request, response);
		return "redirect:/";
	}
	
	@GetMapping("/remove")
	public String remove(HttpServletRequest request, HttpServletResponse response, Principal principal, RedirectAttributes re) {
		log.info(principal.toString());
		String email = principal.getName();
		int isOk = usv.remove(email);
		if(isOk>0) {
			re.addFlashAttribute("remove_msg", "ok");			
		}else {
			re.addFlashAttribute("remove_msg", "fail");						
		}
		logout(request, response);
		return "redirect:/";
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		// 내가 로그인 한 시큐리티의 authentication 객체
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, authentication);
	}
}
