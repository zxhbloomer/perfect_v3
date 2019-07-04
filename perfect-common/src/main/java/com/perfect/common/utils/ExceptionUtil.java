package com.perfect.common.utils;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

@Component
public class ExceptionUtil {

    private static boolean SIMPLE_MODEL;

    @Value("${perfect.config.simple-model}")
    public void setSIMPLE_MODEL(boolean SIMPLE_MODEL) {
        this.SIMPLE_MODEL = SIMPLE_MODEL;
    }

    /**
     * 将异常日志转换为字符串
     * @param e
     * @return
     */
    public static String getException(Exception e) {
        String rtn = "";
        if (SIMPLE_MODEL){
            rtn = e.toString();
            return rtn;
        }
        Writer writer = null;
        PrintWriter printWriter = null;
        try {
            writer = new StringWriter();
            printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            rtn = writer.toString();
            return rtn;
        } finally {
            try {
                if (writer != null)
                    writer.close();
                if (printWriter != null)
                    printWriter.close();
            } catch (IOException e1) {
            }
        }
    }
}
