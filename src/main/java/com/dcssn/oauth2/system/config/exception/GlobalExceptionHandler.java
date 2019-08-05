package com.dcssn.oauth2.system.config.exception;

import com.dcssn.oauth2.system.utils.HttpResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * RestController异常统一处理
 *
 * @author lihy
 * @since 2019/7/30
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * sql异常
     *
     * @param request 请求
     * @param e       异常
     * @return 返回信息
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    public HttpResultUtils.HttpResult handleThrowable(HttpServletRequest request, DataIntegrityViolationException e) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        if (e.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException exception = (ConstraintViolationException) e.getCause();
            return HttpResultUtils.fail(exception.getSQLException().getMessage());
        }
        return HttpResultUtils.fail();
    }

    /**
     * 处理controller 类方法入参校验不通过异常
     *
     * @param request 请求
     * @param e       异常
     * @return 返回信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResultUtils.HttpResult handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder msg = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            msg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(",");
        }
        return HttpResultUtils.fail("参数不合法 " + msg.toString());
    }
}
