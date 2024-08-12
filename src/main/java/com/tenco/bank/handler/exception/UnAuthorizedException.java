package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException{

	private HttpStatus status; // 클라이언트가 보낸 HTTP 요청에 대한 서버의 응답을 코드로 표현 (상태코드)

	// throw new UnAuthorizedException( , )
	public UnAuthorizedException(String message, HttpStatus status) {
		super(message); // 부모 클래스인 RuntimeException의 생성자를 호출하여 message를 전달
		// 그래서 implements 할 때마다 super가 생긴거임...
		this.status = status;
	}
	
}
