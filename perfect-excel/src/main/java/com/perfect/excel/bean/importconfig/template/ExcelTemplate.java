package com.perfect.excel.bean.importconfig.template;

import com.alibaba.fastjson.annotation.JSONField;
import com.perfect.excel.bean.importconfig.template.data.DataCol;
import com.perfect.excel.bean.importconfig.template.data.DataRow;
import com.perfect.excel.bean.importconfig.template.title.TitleRow;
import com.perfect.excel.conf.validator.Validator;
import com.perfect.excel.conf.validator.ValidatorUtil;
import com.perfect.excel.upload.JxlExcelException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * excel模板配置类
 * 
 * @author zxh
 */
public class ExcelTemplate implements Serializable {

    private static final long serialVersionUID = 4795280514712199351L;

    @Getter
    @Setter
    @Deprecated
    @JSONField(serialize = false)
    private List<TitleRow> titleRows = new ArrayList<TitleRow>();

    @Setter
    @Getter
    @JSONField
    private TitleRow titleRow;

    @Setter
    @Getter
    @JSONField
    private DataRow dataRow;

    @Setter
    @Getter
    @JSONField
    private String name;

    /**
     * 往数组中，添加titleRow
     * 
     * @param titleRow
     */
    public void addTitleRow(TitleRow titleRow) {
        titleRows.add(titleRow);
    }

    /**
     * 获取数据行长度，条数
     * 
     * @return
     */
    @JSONField(serialize = false)
    public int getDataRowIndex() {
        return titleRows.size();
    }

    /**
     * 获取列数
     * 
     * @return
     */
    @JSONField(serialize = false)
    public int getColSize() {
        return titleRows.get(0).colSize();
    }

    /**
     * 获取列对象
     * 
     * @return
     */
    @JSONField(serialize = false)
    public List<DataCol> getDataCols() {
        return dataRow.getDataCols();
    }

    /**
     * 根据json字符串，反向生成的Bean，进行添加Validator对象验证的处理
     */
    public void initValidator() {
        // dataCols:数据列，存在多条情况
        DataRow dr = this.dataRow;
        dr.getDataCols().forEach(
            bean -> {
                // 添加验证validator，存在多条情况
                bean.getListValiDatorBean().forEach(
                    v -> {
                        // 获取validator 验证类
                        Validator validator = ValidatorUtil.getValidator(v.getValidtorName());
                        // 获取validator 验证类，对应的参数，存在多个参数情况
                        v.getParam().forEach(
                            p -> {
                                // 设置validator 验证类的参数
                                try {
                                    BeanUtils.setProperty(validator, p.getName(), p.getValue());
                                } catch (IllegalAccessException e) {
                                    throw new JxlExcelException(e);
                                } catch (InvocationTargetException e) {
                                    throw new JxlExcelException(e);
                                }
                            }
                        );
                    }

                );
                // converter为动态自动判断，此处无需考虑
            }
        );

    }

}
