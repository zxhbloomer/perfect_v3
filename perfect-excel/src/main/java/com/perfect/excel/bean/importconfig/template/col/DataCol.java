package com.perfect.excel.bean.importconfig.template.col;

import com.perfect.excel.conf.validator.ColValidateResult;
import com.perfect.excel.conf.validator.Validator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * excel列模板bean
 * @author zxh
 */
public class DataCol implements Serializable {

    private static final long serialVersionUID = 1246107721629872424L;

    /**
     * 列名
     */
    @Getter
    @Setter
    private String name;
    /**
     * 列号
     */
    @Getter
    @Setter
    private int colIndex;
    /**
     * 转换类
     */
    @Getter
    @Setter
    private String convertor;
    /**
     * check类
     */
    private List<Validator> validators = new ArrayList<Validator>();

    /**
     * 构造函数
     * @param name
     */
    public DataCol(String name) {
        this.name = name;
    }

    /**
     * 添加check
     * @param validator
     */
    public void addValidator(Validator validator) {
        validators.add(validator);
    }

    /**
     * 验证
     * @param input
     * @return
     */
    public ColValidateResult validate(String input) {
        ColValidateResult result = new ColValidateResult();
        result.setDataCol(this);
        for (Validator validator : validators) {
            if (!validator.validate(input)) {
                result.setErrorMsg(validator.getErrorMsg());
                break;
            }
        }
        return result;
    }

    /**
     * 查看是否包含验证
     * @return
     */
    public boolean hasValidator() {
        return !validators.isEmpty();
    }
}
