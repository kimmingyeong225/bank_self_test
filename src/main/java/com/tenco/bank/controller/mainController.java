package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.tenco.bank.handler.exception.RedirectException;

@Controller 
public class mainController {
	
	// http://localhost:8080/main-page
	@GetMapping({"/main-page", "/index"})
	public String mainPage() {
		System.out.println("mainPage() 호출 확인");
		
		return "main";
	}
	
	@GetMapping("/error-test1")
	public String errorPage() {
		if(true) {
			throw new RedirectException("잘못된 요청입니다.", HttpStatus.NOT_FOUND);
		}
		return "main";
	}
	
	@GetMapping("/error-test2")
	public String errorData2() {
		if(true) {
			throw new RedirectException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		return "main";
	}
	
	@GetMapping("/error-test3")
	public String errorData3() {
		if(true) {
			throw new RedirectException("잘못된 요청입니다.", HttpStatus.UNAUTHORIZED);
		}
		return "main";
	}

}
