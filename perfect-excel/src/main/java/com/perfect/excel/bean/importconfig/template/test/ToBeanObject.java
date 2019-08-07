package com.perfect.excel.bean.importconfig.template.test;

import com.alibaba.fastjson.JSON;
import com.perfect.common.utils.JsonConvertUtil;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;

/**
 * @author zxh
 * @date 2019-08-07
 */
public class ToBeanObject {
    public static void main(String[] args) {
        String json = "{\"dataRow\":{\"dataCols\":[{\"colIndex\":0,\"convertor\":\"datetime\",\"name\":\"type\"},{\"colIndex\":1,\"convertor\":\"date\",\"listValiDatorBean\":[{\"validtorName\":\"required\"},{\"validtorName\":\"datetime\"}],\"name\":\"code\"},{\"colIndex\":2,\"name\":\"name\"},{\"colIndex\":3,\"name\":\"descr\"},{\"colIndex\":4,\"name\":\"simpleName\"}]},\"titleRow\":{\"cols\":[{\"colSpan\":1,\"title\":\"角色类型\"},{\"colSpan\":1,\"title\":\"角色编码\"},{\"colSpan\":1,\"title\":\"角色名称\"},{\"colSpan\":1,\"title\":\"描述\"},{\"colSpan\":1,\"title\":\"简称\"}]}}\n";
        ExcelTemplate et =  JsonConvertUtil.json2Obj(json, ExcelTemplate.class);
        System.out.println("");
    }
}
