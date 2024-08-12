package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bank.repository.model.User;

@Mapper // Interface 매퍼 등록
public interface UserRepository {
	public int insert(User user);
	public int upsateById(User user);
	public int deleteById(Integer id);
	public User findById(Integer id);
	public List<User> findAll();
	
	// 매개변수 2개 이상 시 반드시 @Param 어노테이션 사용!
	// 로그인 기능을 구현할 때 사용 (=자격증명..)
	// 만들어주는 이유! =  db조회 -> 반환
	public User findByUsernamrAndPassword(@Param("username") String username, @Param("password") String password);
	
}
