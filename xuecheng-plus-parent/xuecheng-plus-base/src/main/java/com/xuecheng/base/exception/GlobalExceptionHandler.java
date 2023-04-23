package com.xuecheng.base.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理自定义异常
     *
     * @param xcpe
     * @return
     */
    @ResponseBody
    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(XueChengPlusException xcpe) {
        log.info("自定义异常信息：{}", xcpe.getErrMessage());

        return new RestErrorResponse(xcpe.getErrMessage());
    }


    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse sysException(Exception e) {
        log.info("系统异常信息：{}", e.getMessage());

        return new RestErrorResponse(e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse sysException(MethodArgumentNotValidException e) {
        log.info("请求参数异常信息：{}", e.getMessage());

        //StringBuffer str = new StringBuffer("");
        List<String> errList = new ArrayList<>();
        e.getBindingResult().getFieldErrors().stream().forEach(item -> {
            errList.add(item.getDefaultMessage());
        });

        String resStr = StringUtils.join(errList, "||");

        return new RestErrorResponse(resStr);
    }


}
