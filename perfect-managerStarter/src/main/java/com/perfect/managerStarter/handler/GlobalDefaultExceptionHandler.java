package com.perfect.managerStarter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e,
                e.getMessage(),
                request);
    }

}
