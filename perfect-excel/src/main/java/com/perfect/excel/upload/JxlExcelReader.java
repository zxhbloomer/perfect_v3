package com.perfect.excel.upload;

import com.perfect.common.utils.string.StringUtil;
import com.perfect.excel.bean.importconfig.template.data.DataCol;
import com.perfect.excel.conf.DummyTitleCol;
import com.perfect.excel.bean.importconfig.template.title.TitleCol;
import com.perfect.excel.bean.importconfig.template.title.TitleRow;
import com.perfect.excel.conf.convertor.ConvertorUtil;
import com.perfect.excel.conf.validator.ColValidateResult;
import com.perfect.excel.conf.validator.RowValidateResult;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入类
 * @author zxh
 */
@Slf4j
public class JxlExcelReader extends JxlExcel {

    private InputStream is;

    private List<RowValidateResult> rowValidateResults = new ArrayList<RowValidateResult>();

    /**
     * 读取文件
     * @param excelFile
     * @throws FileNotFoundException
     */
    public JxlExcelReader(File excelFile) throws FileNotFoundException {
        this(new FileInputStream(excelFile));
    }

    /**
     * 读取流
     * @param is
     */
    public JxlExcelReader(InputStream is) {
        this.is = is;
    }

    /**
     * 数据是否有异常
     * @return
     */
    public boolean isDataValid() {
        return rowValidateResults.size() == 0;
    }

    /**
     * 返回异常数据
     * @return
     */
    public List<RowValidateResult> getRowValidateResults() {
        return rowValidateResults;
    }

    /**
     * 读取后，泛型返回
     * @param clasz
     * @param <T>
     * @return
     */
    public <T> List<T> readBeans(final Class<T> clasz) {
        return read(new ReadPolicy<T>() {
            @Override
            protected T newRowData() {
                try {
                    return clasz.newInstance();
                } catch (InstantiationException e) {
                    throw new JxlExcelException(e);
                } catch (IllegalAccessException e) {
                    throw new JxlExcelException(e);
                }
            }

            @Override
            protected void setColData(T rowData, DataCol dataCol, Object colDataVal) {
                try {
                    BeanUtils.setProperty(rowData, dataCol.getName(), colDataVal);
                } catch (IllegalAccessException e) {
                    throw new JxlExcelException(e);
                } catch (InvocationTargetException e) {
                    throw new JxlExcelException(e);
                }
            }

        });
    }

    /**
     * 读取后，以数组方式来返回
     * @return
     */
    public List<String[]> readArrays() {
        return read(new ReadPolicy<String[]>() {
            @Override
            protected String[] newRowData() {
                return new String[excelTemplate.getColSize()];
            }

            @Override
            protected void setColData(String[] rowData, DataCol dataCol, Object colDataVal) {
                rowData[dataCol.getColIndex()] = StringUtil.toString(colDataVal);
            }
        });
    }

    /**
     * 读取后，以List<Map>方式来返回
     * @return
     */
    public List<Map<String, Object>> readMaps() {
        return read(new ReadPolicy<Map<String, Object>>() {
            @Override
            protected Map<String, Object> newRowData() {
                return new HashMap<String, Object>();
            }

            @Override
            protected void setColData(Map<String, Object> rowData, DataCol dataCol, Object colDataVal) {
                rowData.put(dataCol.getName(), colDataVal);
            }
        });
    }

    /**
     * 以 读取策略方式来进行读取，这个是的核心
     * @param readPolicy
     * @param <T>
     * @return
     */
    private <T> List<T> read(ReadPolicy<T> readPolicy) {
        checkTemplate();
        Workbook wb;
        try {
            wb = Workbook.getWorkbook(is);
        } catch (BiffException e) {
            throw new JxlExcelException(e);
        } catch (IOException e) {
            throw new JxlExcelException(e);
        }
        try {
            Sheet sheet = wb.getSheet(0);
            readPolicy.checkTemplateTitles(sheet);
            return readPolicy.readDatasFromSheet(sheet);
        } finally {
            wb.close();
        }
    }

    /**
     * 获取读取excel的策略，并执行策略（check）
     * @param <T>
     */
    abstract class ReadPolicy<T> {
        /**
         * 设置列数据
         * @param rowData
         * @param dataCol
         * @param colDataVal
         */
        protected abstract void setColData(T rowData, DataCol dataCol, Object colDataVal);

        /**
         * 新的一行数据
         * @return
         */
        protected abstract T newRowData();

        List<T> readDatasFromSheet(Sheet sheet) {
            List<T> datas = new ArrayList<T>();
            for (int row = excelTemplate.getDataRowIndex(); row < sheet
                    .getRows(); row++) {
                List<DataCol> dataCols = excelTemplate.getDataCols();
                T rowData = newRowData();

                boolean isRowDataValid = true;
                RowValidateResult rowValidateResult = new RowValidateResult();
                for (int col = 0; col < dataCols.size(); col++) {
                    String value = sheet.getCell(col, row).getContents()
                            .trim();
                    DataCol dataCol = dataCols.get(col);
                    if (dataCol.hasValidator()) {
                        ColValidateResult colValidateResult = dataCol.validate(value);
                        rowValidateResult.setRowIndex(row);
                        boolean isColDataValid = colValidateResult.isSuccess();
                        isRowDataValid = isRowDataValid && isColDataValid;
                        if (!isColDataValid) {
                            rowValidateResult.addColValidateResult(colValidateResult);
                        }
                    }
                    if (isRowDataValid) {
                        String convertor = dataCol.getConvertor();
                        Object colDataVal = null;
                        if (StringUtils.isNotEmpty(convertor)) {
                            colDataVal = ConvertorUtil.convertToType(value, convertor);
                        } else {
                            colDataVal = value;
                        }
                        setColData(rowData, dataCol, colDataVal);
                    }
                }
                if (isRowDataValid) {
                    datas.add(rowData);
                } else {
                    rowValidateResults.add(rowValidateResult);
                }
            }
            return datas;
        }

        /**
         * 检查模板和excel是否匹配
         * @param sheet
         */
        void checkTemplateTitles(Sheet sheet) {
            if (sheet.getColumns() != excelTemplate.getColSize()) {
                throw new JxlExcelException(String.format(
                        "读取的excel与模板不匹配：期望%s列，实际为%s列",
                        excelTemplate.getColSize(), sheet.getColumns()));
            }
            List<TitleRow> titleRows = excelTemplate.getTitleRows();
            StringBuffer errorMsg = new StringBuffer();
            for (int row = 0; row < titleRows.size(); row++) {
                TitleRow titleRow = titleRows.get(row);
                for (int col = 0; col < titleRow.colSize(); col++) {
                    TitleCol titleCol = titleRow.getCol(col);
                    if (titleCol instanceof DummyTitleCol) {
                        continue;
                    }
                    String value = sheet.getCell(col, row).getContents().trim();
                    if (!value.equals(titleCol.getTitle())) {
                        errorMsg.append(String.format(
                                "第%s行第%s列期望[%s]，实际为[%s]\n", row + 1, col + 1,
                                titleCol.getTitle(), value));
                    }
                }
            }
            if (errorMsg.length() > 0) {
                errorMsg.deleteCharAt(errorMsg.length() - 1);
                throw new JxlExcelException("读取的excel与模板不匹配：" + errorMsg);
            }
        }
    }

}
