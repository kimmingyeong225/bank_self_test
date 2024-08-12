package com.tenco.bank.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.DataDeliveryException;
import com.tenco.bank.handler.exception.RedirectException;
import com.tenco.bank.handler.exception.UnAuthorizedException;

@ControllerAdvice // -> IoC 대상 
public class GlobalControllerAdvice {
	
	/**
	 * 모든 예외 클래스를 알 수 없기 때문에 로깅으로 확인할 수 있도록 설정
	 * 로깅처리 - 동기적 방식(System.out.println)
	 */
	@ExceptionHandler (Exception.class) // 에러를 잡아서 메서드로 처리
	public void exception(Exception e) {
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
	}

	/**
	 * Data로 예외를 내려주는 방법
	 * @ResponseBody 활용
	 * 브라우저에서 자바스크립트 코드로 동작 하게 됨
	 * 
	 * 예외를 내릴 때 데이터를 내리고 싶다면 @RestControllerAdvice 를 사용하면 됨
	 * 단. @ControllerAdvice 사용하고 있다면 @ResponseBody 를 붙여서 사용하면 됨
	 */
	@ResponseBody
	@ExceptionHandler(DataDeliveryException.class)
	public String dataDeleveryExcption(DataDeliveryException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + e.getMessage() + "')");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString();
	}
	
	@ResponseBody
	@ExceptionHandler(UnAuthorizedException.class)
	public String unAuthorizedException(UnAuthorizedException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + e.getMessage() + "')");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString();
	}
	
	/**
	 * 에러 페이지로 이동 처리
	 * JSP로 이동시 데이터를 담아서 보내는 방법
	 * ModelAndView, Model 사용가능
	 */
	// ModelAndView -> 컨트롤러에서 뷰로 데이터 전달(뷰 페이지 이름까지 함께 전달)
	public ModelAndView redirectException(RedirectException e) {
		ModelAndView modelAndView = new ModelAndView("errorPage");
		modelAndView.addObject("statusCode", e.getStatus().value());
		modelAndView.addObject("message", e.getMessage());
		return modelAndView; // 페이지 반환 + 데이터 내려줌
	}

}
