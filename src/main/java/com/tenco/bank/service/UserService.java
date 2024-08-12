package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInDTO;
import com.tenco.bank.dto.SignUpDTO;
import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.RedirectException;
import com.tenco.bank.repository.interfaces.UserRepository;
import com.tenco.bank.repository.model.User;

@Service // IoC
public class UserService {

	// @Autowired // DI 생성자 의존 주입
	private UserRepository userRepository;
	
	@Autowired // DI 생성자 의존 주입
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	@Transactional // 선언적 트랜잭션 처리 -> 예외 처리..
	// 값을 넣었을 때 발생되는 예외처리...
	public void createUser(SignUpDTO dto) {
		int result = 0;
		try {
			result = userRepository.insert(dto.toUser());
		} catch (DataAccessException e) {
			throw new DataDeliveryException("잘못된 처리입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
		}
		// 실행되면 result 값이 1이 되는데 1이 아니라면 회원가입 실패
		if(result != 1) {
			throw new DataDeliveryException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	public User readUser(SignInDTO dto) {
		// 유효성 검사는 Controller에서 먼저 하기!
		User userEntity = null; // 지역 변수 선언
		try {
			userEntity = userRepository.findByUsernamrAndPassword(dto.getUsername(), dto.getPassword());
		} catch (DataAccessException e) {
			throw new DataDeliveryException("잘못된 처리 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			throw new RedirectException("알 수 없는 오류", HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		if(userEntity == null) {
			throw new DataDeliveryException("아이디 혹은 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
		}
		
		return userEntity;
	}
	
	
	
}//
