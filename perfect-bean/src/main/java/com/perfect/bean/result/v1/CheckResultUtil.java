package com.perfect.bean.result.v1;

import com.perfect.bean.pojo.CheckResult;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author zxh
 * @date 2019/8/30
 */
public class CheckResultUtil {

    /**
     * check无误
     * @return
     */
    public static CheckResult OK() {
        return CheckResult.builder()
            .data(null)
            .message("")
            .success(true)
            .build();
    }

    /**
     * check有错
     * @return
     */
    public static CheckResult NG(String msg) {
        return CheckResult.builder()
            .data(null)
            .message(msg)
            .success(false)
            .build();
    }

    /**
     * check有错
     * @return
     */
    public static CheckResult NG(String msg, T _data ) {
        return CheckResult.builder()
            .data(_data)
            .message(msg)
            .success(false)
            .build();
    }

}
