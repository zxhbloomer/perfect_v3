package com.perfect.managerStarter.handler;

import com.perfect.common.utils.ExceptionUtil;
import com.perfect.common.utils.result.ResultUtil;
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
//        return ResponseExceptionBean.builder().code(500).msg(e.getMessage()).build();
       return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                ExceptionUtil.getException(e),
                e.getMessage(),
                request);
    }

}
