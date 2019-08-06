package com.perfect.excel.bean.importconfig.template.row;

import com.perfect.excel.bean.importconfig.template.col.DataCol;
import lombok.Getter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * excel行模板类
 * @author zxh
 */
public class DataRow implements Serializable {

    private static final long serialVersionUID = -3512002550272910844L;

    /**
     * 列数据
     */
    @Getter
    private List<DataCol> dataCols = new ArrayList<DataCol>();

    /**
     * 添加列名
     * @param names
     */
    public void addDataCol(String... names) {
        for (String name : names) {
            addDataCol(new DataCol(name));
        }
    }

    /**
     * 添加列名
     * @param dataCol
     */
    public void addDataCol(DataCol dataCol) {
        dataCol.setColIndex(dataCols.size());
        dataCols.add(dataCol);
    }

    /**
     * 列数
     * @return
     */
    public int colSize() {
        return dataCols.size();
    }

}
