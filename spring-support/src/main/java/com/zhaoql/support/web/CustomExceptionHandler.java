package com.zhaoql.support.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.zhaoql.core.domain.ErrorResult;
import com.zhaoql.core.exception.CustomException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
    private ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    /**
     * 处理自定义异常
     *
     * @param throwable
     * @param request
     * @return
     */
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorResult> handleException(CustomException throwable,
                                                                HttpServletRequest request) {
        logger.error("", throwable);
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        errorAttributes.getErrorAttributes(requestAttributes, false).get("javax.servlet.error.status_code");
        return new ResponseEntity<ErrorResult>(new ErrorResult(throwable.getClass().getName(), throwable.getMessage(), throwable.getErrorCode()), new HttpHeaders(), HttpStatus.OK);

    }

    /**
     * 处理未知异常
     *
     * @param throwable
     * @param request
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    ResponseEntity<ErrorResult> handleException(Throwable throwable, HttpServletRequest request) {
        logger.warn("异常处理");
        if (throwable instanceof HystrixBadRequestException && throwable.getCause() instanceof CustomException) {
            return handleException((CustomException) throwable.getCause(), request);
        } else {
            return handleException(new CustomException("未知错误", throwable), request);
        }
    }

    /**
     * 处理Hystrix异常
     *
     * @param
     * @param
     * @return
     */
    @ExceptionHandler(HystrixRuntimeException.class)
    ResponseEntity<ErrorResult> handleHystrixException(Throwable throwable, HttpServletRequest request) {
        if (throwable.getCause() instanceof CustomException) {
            return handleException((CustomException) throwable.getCause(), request);
        } else {
            return handleException(throwable.getCause(), request);
        }
    }

}