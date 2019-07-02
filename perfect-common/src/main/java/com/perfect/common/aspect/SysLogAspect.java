package com.perfect.common.aspect;


import com.perfect.bean.bo.sys.SysLogBO;
import com.perfect.common.annotation.SysLog;
import com.perfect.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Pointcut("@annotation(com.perfect.spring.aspect.annotation.SysLog)")
    public void sysLogAspect(){}

//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        // 记录下请求内容
//        log.debug("======================日志开始================================");
//        log.debug("URL : " + request.getRequestURL().toString());
//        log.debug("HTTP_METHOD : " + request.getMethod());
//        log.debug("IP : " + request.getRemoteAddr());
//        log.debug("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        log.debug("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//        log.debug("======================日志结束================================");
//    }
//
//    @AfterReturning(returning = "result", pointcut = "webLog()")
//    public void doAfterReturning(Object result) throws Throwable {
//        // 处理完请求，返回内容
//        log.debug("执行结果 : " + result);
//    }

    /**
     * 环绕通知 @Around  ， 当然也可以使用 @Before (前置通知)  @After (后置通知)
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("sysLogAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        BigDecimal time =  new BigDecimal(System.currentTimeMillis() - beginTime);
        BigDecimal divide = time.divide(BigDecimal.valueOf(1000));
        try {
            saveLog(point, divide);
        } catch (Exception e) {
        }
        return result;
    }



    /**
     * 保存日志
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, BigDecimal time) {

        // 获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        SysLogBO sysLogBO = SysLogBO.builder()
                                    .className(joinPoint.getTarget().getClass().getName())
                                    .httpMethod(request.getMethod())
                                    .classMethod(((MethodSignature) joinPoint.getSignature()).getName())
                                    .params( Arrays.toString(joinPoint.getArgs()))
                                    .execTime(time.toString())
                                    .remark(sysLog.value())
                                    .createDate(dateFormat.format(new Date()))
                                    .url(request.getRequestURL().toString())
                                    .ip(IPUtil.getIpAdd())
                                    .build();

        log.debug("======================日志开始================================");
        log.debug("日志名称         : " + sysLogBO.getRemark());
        log.debug("URL             : " + sysLogBO.getUrl());
        log.debug("HTTP方法         : " + sysLogBO.getHttpMethod());
        log.debug("IP               : " + sysLogBO.getIp());
        log.debug("类名             : " + sysLogBO.getClassName());
        log.debug("类方法           : " + sysLogBO.getClassMethod());
        log.debug("执行时间         : " + sysLogBO.getExecTime() + "秒");
        log.debug("执行日期         : " + sysLogBO.getCreateDate());
        log.debug("参数             : " + sysLogBO.getParams());
        log.debug("======================日志结束================================");
    }
}