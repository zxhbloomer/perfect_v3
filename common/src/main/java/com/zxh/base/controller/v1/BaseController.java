package com.zxh.base.controller.v1;

import com.zxh.Enum.ResultEnum;
import com.zxh.bean.pojo.JSONResult;
import com.zxh.utils.DateTimeUtil;
import com.zxh.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * controller父类
 */
@Slf4j
public class BaseController {

//    @GetMapping = @RequestMapping(method = RequestMethod.GET)
//    @PostMapping = @RequestMapping(method = RequestMethod.POST)
//    @PutMapping = @RequestMapping(method = RequestMethod.PUT)          --不用
//    @DeleteMapping = @RequestMapping(method = RequestMethod.DELETE)    --不用

    /**
     * 失败返回
     * @param retmsg
     * @return
     */
    public JSONResult returnNG(String retmsg, HttpServletRequest request) {
        return JSONResult
                .builder()
                .timestamp(DateTimeUtil.getSystemDateYYYYMMDDHHMMSS())
                .status(ResultEnum.FAIL.getCode())
                .message(retmsg)
                .path(request.getRequestURL().toString())
                .success(false)
                .data(null)
                .build();
    }

    /**
     * 得到request对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

//    /** ajax登录异常处理 **/
//    @ExceptionHandler({ AjaxLoginException.class })
//    @ResponseBody
//    public AjaxResult ajaxLoginExceptionHandler(AjaxLoginException e) {
//        log.error("登录请求发生异常:", e);
//        return new AjaxResult(e.getKey(), e.getMessage());
//    }
//
//    /** 普通登录异常处理 **/
//    @ExceptionHandler({ LoginException.class })
//    public String loginExceptionHandler(LoginException e, HttpServletRequest request) {
//        log.error("登录请求发生异常:", e);
//        request.setAttribute("err", e.getMessage());
//        return "forward:/";
//    }

//    /** 普通权限异常处理 **/
//    @ExceptionHandler({ PermissionException.class })
//    public String permissonExceptionHandler(PermissionException e) {
////        return "common/no_permisson";
//        return ErrorResult
//                .builder()
//                .timestamp(DateTimeUtil.getSystemDateYYYYMMDDHHMMSS())
//                .status(ResultEnum.FAIL.getCode())
//                .error(retmsg)
//                .exception(null)
//                .message(retmsg)
//                .path(request.getRequestURL().toString())
//                .build();
//    }
//
//    /** ajax权限异常处理 **/
//    @ExceptionHandler({ AjaxPermissionException.class })
//    @ResponseBody
//    public AjaxResult ajaxPermissionExceptionHandler(AjaxPermissionException e) {
//        return new AjaxResult(e.getKey(), e.getMessage());
//    }
//
//    /** 频繁请求异常处理 **/
//    @ExceptionHandler({ MalciousException.class })
//    public String malExceptionHandler(MalciousException e) {
//        return "common/mal_request";
//    }
//
//    /** 公共异常处理 **/
//    @ExceptionHandler({ Exception.class })
//    public Object exceptionHandler(Exception e, HttpServletRequest request) {
//        ParamData params = new ParamData();
//        log.info("");
//        StringBuilder sb = new StringBuilder(params.getString("loginIp")).append(request.getRequestURI()).append("请求发生异常:")
//                .append(request.getServletPath()).append(":").append(params);
//        log.error(sb.toString(), e);
//        return "common/500";
//    }

    public void logBefore(String desc) {
        HttpServletRequest request = getRequest();
        log.error("");
        StringBuilder sb = new StringBuilder(IPUtil.getIpAdd(request)).append(desc).append(":").append(request.getServletPath());
        log.error(sb.toString());
    }
}
