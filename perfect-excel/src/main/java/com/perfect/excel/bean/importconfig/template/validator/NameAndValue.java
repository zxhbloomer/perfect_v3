package com.perfect.excel.bean.importconfig.template.validator;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

/**
 * @author zxh
 * @date 2019-08-06
 */
@Data
public class NameAndValue implements Serializable {
    private static final long serialVersionUID = 467871600970028042L;

    private String name;
    private Object value;
}
