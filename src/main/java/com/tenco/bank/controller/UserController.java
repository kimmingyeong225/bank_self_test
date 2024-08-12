package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInDTO;
import com.tenco.bank.dto.SignUpDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller // IoC
@RequestMapping("/user") // 클래스 연결
public class UserController {

	// @Autowired // DI 처리
	private UserService userService;
	private final HttpSession session;

	// DI 처리
	@Autowired
	public UserController(UserService service, HttpSession session) {
		this.userService = service;
		this.session = session;
	}

	/**
	 * 로그인 페이지 요청 주소 http://localhost:8080/user/sign-up
	 */
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "user/signUp";
	}

	/**
	 * 회원 가입 로직 처리 요청
	 */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpDTO dto) {
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException("username을 입력 하세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new DataDeliveryException("password을 입력 하세요", HttpStatus.BAD_REQUEST);
		}

		if (dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new DataDeliveryException("fullname을 입력 하세요", HttpStatus.BAD_REQUEST);
		}

		userService.createUser(dto);

		return "redirect:/index";
	}

	@GetMapping("/sign-in")
	public String signInPage() {
		return "user/signIn";

	}

	/**
	 * 회원가입 요청 처리 주소 http://localhost:8080/user/sign-in
	 * 
	 * @param dto
	 * @return
	 */
	@PostMapping("/sign-in")
	public String signProc(SignInDTO dto) {

		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}

		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new DataDeliveryException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}

		// 서비스 호출
		User principal = userService.readUser(dto);
		session.setAttribute("principal", principal);

		return "redirect:index";

	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); // 로그아웃 됨 = 세션 종료(무효화) -> 데이터 삭제
		return "redirect:/user/sign-in";
	}

}//
