package com.perfect.managerstarter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfect.common.exception.UpdateErrorException;
import com.perfect.common.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zxh
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    /**
     * 其他的错误
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                 ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        e,
                        e.getMessage(),
                        request)
        );
    }

    /**
     * 更新出错时，设置返回的head，body
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = UpdateErrorException.class)
    @ResponseBody
    public ResponseEntity<Object> updateErrorExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e,
                e.getMessage(),
                request)
        );
    }
}
