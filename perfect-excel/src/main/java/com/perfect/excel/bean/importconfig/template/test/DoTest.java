package com.perfect.excel.bean.importconfig.template.test;

import com.perfect.excel.bean.importconfig.template.data.DataCol;
import com.perfect.excel.bean.importconfig.template.data.DataRow;
import com.perfect.excel.bean.importconfig.template.title.TitleCol;
import com.perfect.excel.conf.validator.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxh
 * @date 2019年 08月06日 21:38:13
 */
public class DoTest {


    public List<TitleCol> setTitleRow(){
        List<TitleCol> rows = new ArrayList<>();

        TitleCol titleCol1 = new TitleCol("角色类型");
        TitleCol titleCol2 = new TitleCol("角色编码");
        TitleCol titleCol3 = new TitleCol("角色名称");
        TitleCol titleCol4 = new TitleCol("描述");
        TitleCol titleCol5 = new TitleCol("简称");

        rows.add(titleCol1);
        rows.add(titleCol2);
        rows.add(titleCol3);
        rows.add(titleCol4);
        rows.add(titleCol5);
        return rows;
    }

    public DataRow setDataRow(){
        DataRow row = new DataRow();

        DataCol dataCol1 = new DataCol("type");
        dataCol1.setConvertor("datetime");
        DataCol dataCol2 = new DataCol("code");
        dataCol2.setConvertor("date");
        Validator
        dataCol2.addValidator("date");

        DataCol dataCol3 = new DataCol("name");
        DataCol dataCol4 = new DataCol("descr");
        DataCol dataCol5 = new DataCol("simpleName");




        row.addDataCol(dataCol1);
        row.addDataCol(dataCol2);
        row.addDataCol(dataCol3);
        row.addDataCol(dataCol4);
        row.addDataCol(dataCol5);

        return row;
    }
}
