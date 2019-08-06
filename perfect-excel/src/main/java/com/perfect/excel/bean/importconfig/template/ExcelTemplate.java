package com.perfect.excel.bean.importconfig.template;

import com.perfect.excel.bean.importconfig.template.data.DataCol;
import com.perfect.excel.bean.importconfig.template.data.DataRow;
import com.perfect.excel.bean.importconfig.template.title.TitleRow;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * excel模板配置类
 * @author zxh
 */
public class ExcelTemplate implements Serializable {

    private static final long serialVersionUID = 4795280514712199351L;

    @Getter
    private List<TitleRow> titleRows = new ArrayList<TitleRow>();
    @Setter
    @Getter
    private DataRow dataRow;
    @Setter
    @Getter
    private String name;

    /**
     * 往数组中，添加titleRow
     * @param titleRow
     */
    public void addTitleRow(TitleRow titleRow) {
        titleRows.add(titleRow);
    }

    /**
     * 获取数据行长度，条数
     * @return
     */
    public int getDataRowIndex() {
        return titleRows.size();
    }

    /**
     * 获取列数
     * @return
     */
    public int getColSize() {
        return titleRows.get(0).colSize();
    }

    /**
     * 获取列对象
     * @return
     */
    public List<DataCol> getDataCols() {
        return dataRow.getDataCols();
    }

}
