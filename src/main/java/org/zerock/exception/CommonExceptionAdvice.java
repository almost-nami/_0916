package org.zerock.exception;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

// @ControllerAdvice : 해당 객체가 스프링의 컨트롤러에서 발생하는 예외를 처리하는 존재임을 명시
// @ExceptionHandler : 해당 메서드가 파라미터로 들어가는 예외 타입을 처리한다는 것을 의미
@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {

    // Exception.class -> 모든 예외에 대한 처리
    // 500 에러는 Internal Server Error이므로 @ExceptionHandler를 이용해서 처리가능
    @ExceptionHandler(Exception.class)
    public String except(Exception ex, Model model) {
        log.error("Exception ......" + ex.getMessage());
        model.addAttribute("exception", ex);
        log.error(model);

        return "sample/error_page";
    }

    // NoHandlerFoundException -> 404 에러처리
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException ex) {

        return "sample/custom404";
    }
}
