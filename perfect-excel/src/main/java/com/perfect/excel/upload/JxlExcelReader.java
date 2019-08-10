package com.perfect.excel.upload;

import com.perfect.common.utils.string.StringUtil;
import com.perfect.excel.bean.importconfig.template.data.DataCol;
import com.perfect.excel.bean.importconfig.template.title.DummyTitleCol;
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
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导入类
 * 
 * @author zxh
 */
@Slf4j
public class JxlExcelReader extends JxlExcel {

    private InputStream is;

    private List<RowValidateResult> rowValidateResults = new ArrayList<RowValidateResult>();

    /**
     * 读取文件
     * 
     * @param excelFile
     * @throws FileNotFoundException
     */
    public JxlExcelReader(File excelFile) throws FileNotFoundException {
        this(new FileInputStream(excelFile));
    }

    /**
     * 读取流
     * 
     * @param is
     */
    public JxlExcelReader(InputStream is) {
        this.is = is;
    }

    /**
     * 数据是否有异常
     * 
     * @return
     */
    public boolean isDataValid() {
        return rowValidateResults.size() == 0;
    }

    /**
     * 返回异常数据
     * 
     * @return
     */
    public List<RowValidateResult> getRowValidateResults() {
        return rowValidateResults;
    }

    /**
     * 读取后，泛型返回
     * 
     * @param clasz
     * @param <T>
     * @return
     */
    public <T> List<T> readBeans(final Class<T> clasz) throws IOException {
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
     * 
     * @return
     */
    public List<String[]> readArrays() throws IOException {
        return read(new ReadPolicy<String[]>() {
            @Override
            protected String[] newRowData() {
                return new String[excelTemplate.getColSize()];
            }

            @Override
            protected void setColData(String[] rowData, DataCol dataCol, Object colDataVal) {
                rowData[dataCol.getIndex()] = StringUtil.toString(colDataVal);
            }
        });
    }

    /**
     * 读取后，以List<Map>方式来返回
     * 
     * @return
     */
    public List<Map<String, Object>> readMaps() throws IOException {
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
     * 
     * @param readPolicy
     * @param <T>
     * @return
     */
    private <T> List<T> read(ReadPolicy<T> readPolicy) throws IOException {
        checkTemplate();
        // 文件分析，判断是否是excel文档
        if (FileMagic.valueOf(is) == FileMagic.OLE2) {
            // Office 2003 ，xls
            Workbook workbook = null;
            try {
                workbook = Workbook.getWorkbook(is);
            } catch (BiffException e) {
                throw new JxlExcelException(e);
            } catch (IOException e) {
                throw new JxlExcelException(e);
            }
            try {
                Sheet sheet = workbook.getSheet(0);
                readPolicy.checkTemplateTitles(sheet);
                return readPolicy.readDatasFromSheet(sheet);
            } finally {
                if (workbook != null) {
                    workbook.close();
                }
            }
        } else {
            // Office 2007 +，xlsx
            XSSFWorkbook xssfWorkbook;
            try {
                xssfWorkbook = new XSSFWorkbook(is);
            } catch (Exception e) {
                throw new JxlExcelException(e);
            }
            try {
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                readPolicy.checkTemplateTitles(xssfSheet);
                return readPolicy.readDatasFromSheet(xssfSheet, xssfWorkbook);
            } finally {
                if (xssfWorkbook != null) {
                    xssfWorkbook.close();
                }
            }
        }
    }

    /**
     * 判断是否诗xlsx or xls
     * 
     * @return
     */
    private boolean getXlsOrXlsx() throws IOException {
        // 文件分析，判断是否是excel文档
        if (FileMagic.valueOf(is) == FileMagic.OLE2) {
            // Office 2003 ，xls
            return true;
        } else {
            // Office 2007 +，xlsx
            return false;
        }
    }

    /**
     * 获取读取excel的策略，并执行策略（check）
     * 
     * @param <T>
     */
    abstract class ReadPolicy<T> {
        /**
         * 设置列数据
         * 
         * @param rowData
         * @param dataCol
         * @param colDataVal
         */
        protected abstract void setColData(T rowData, DataCol dataCol, Object colDataVal);

        /**
         * 新的一行数据
         * 
         * @return
         */
        protected abstract T newRowData();

        /**
         * 读取数据
         * 
         * @param sheet
         * @return
         */
        List<T> readDatasFromSheet(XSSFSheet sheet, XSSFWorkbook xssfWorkbook) {
            List<T> datas = new ArrayList<T>();
            for (int row = excelTemplate.getDataRowIndex(); row <= sheet.getLastRowNum(); row++) {
                List<DataCol> dataCols = excelTemplate.getDataCols();
                T rowData = newRowData();

                boolean isRowDataValid = true;
                RowValidateResult rowValidateResult = new RowValidateResult();
                for (int col = 0; col < dataCols.size(); col++) {
                    String value = getCellValue(row, col, sheet).trim();
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
                    // 添加错误数据
                    setCellValue(xssfWorkbook, row, dataCols.size() + 1, sheet, rowValidateResult.getErrors(excelTemplate.getTitleRows()));
                }
            }
            return datas;
        }

        /**
         * 读取数据
         * 
         * @param sheet
         * @return
         */
        List<T> readDatasFromSheet(Sheet sheet) {
            List<T> datas = new ArrayList<T>();
            for (int row = excelTemplate.getDataRowIndex(); row < sheet.getRows(); row++) {
                List<DataCol> dataCols = excelTemplate.getDataCols();
                T rowData = newRowData();

                boolean isRowDataValid = true;
                RowValidateResult rowValidateResult = new RowValidateResult();
                for (int col = 0; col < dataCols.size(); col++) {
                    String value = sheet.getCell(col, row).getContents().trim();
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
         * 
         * @param sheet
         */
        void checkTemplateTitles(Sheet sheet) {
            if (sheet.getColumns() != excelTemplate.getColSize()) {
                throw new JxlExcelException(
                    String.format("读取的excel与模板不匹配：期望%s列，实际为%s列", excelTemplate.getColSize(), sheet.getColumns()));
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
                        errorMsg.append(
                            String.format("第%s行第%s列期望[%s]，实际为[%s]\n", row + 1, col + 1, titleCol.getTitle(), value));
                    }
                }
            }
            if (errorMsg.length() > 0) {
                errorMsg.deleteCharAt(errorMsg.length() - 1);
                throw new JxlExcelException("读取的excel与模板不匹配：" + errorMsg);
            }
        }

        /**
         * 检查模板和excel是否匹配
         * 
         * @param sheet
         */
        void checkTemplateTitles(XSSFSheet sheet) {
            if (sheet.getRow(0).getPhysicalNumberOfCells() != excelTemplate.getColSize()) {
                throw new JxlExcelException(String.format("读取的excel与模板不匹配：期望%s列，实际为%s列", excelTemplate.getColSize(),
                    sheet.getRow(0).getPhysicalNumberOfCells()));
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
                    // String value = sheet.getCell(col, row).getContents().trim();
                    String value = getCellValue(row, col, sheet).trim();
                    if (!value.equals(titleCol.getTitle())) {
                        errorMsg.append(
                            String.format("第%s行第%s列期望[%s]，实际为[%s]\n", row + 1, col + 1, titleCol.getTitle(), value));
                    }
                }
            }
            if (errorMsg.length() > 0) {
                errorMsg.deleteCharAt(errorMsg.length() - 1);
                throw new JxlExcelException("读取的excel与模板不匹配：" + errorMsg);
            }
        }

        /**
         * 设置单元格中的值
         * 
         * @return
         */
        private void setCellValue(XSSFWorkbook xssfWorkbook, int rowId, int col, XSSFSheet sheet, String error) {
            Font font = xssfWorkbook.createFont();
            font.setFontName("微软雅黑");
            font.setColor(Font.COLOR_RED);
            font.setBold(true);
            CellStyle cellStyle = xssfWorkbook.createCellStyle();
            cellStyle.setFont(font);

            XSSFRow row = sheet.getRow(rowId);
            // 创建头部head
            Cell cellHead = row.createCell(0);
            cellHead.setCellStyle(cellStyle);
            cellHead.setCellValue("导入错误");
            // 创建错误列
            Cell cell = row.createCell(col);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(error);
        }

        /**
         * 获取单元格中的值
         * 
         * @return
         */
        String getCellValue(int rowId, int col, XSSFSheet sheet) {
            XSSFRow row = sheet.getRow(rowId);
            XSSFCell cell = row.getCell(col);

            // 返回值
            String rtn = "";
            // 如果cell中没有值
            if (cell == null) {
                log.debug("cell的value: rowid=" + rowId + "；col=" + col + "；cellvalue:" + rtn);
                return rtn;
            }

            if (cell.getCellType() == CellType.NUMERIC) {
                rtn = String.valueOf(cell.getNumericCellValue());
            } else if (cell.getCellType() == CellType.BOOLEAN) {
                rtn = String.valueOf(cell.getBooleanCellValue());
            } else if (cell.getCellType() == CellType.STRING) {
                rtn = String.valueOf(cell.getStringCellValue());
            } else if (cell.getCellType() == CellType.BLANK) {
                rtn = "";
            } else {
                rtn = cell.getStringCellValue();
            }
            log.debug("cell的value: rowid=" + rowId + "；col=" + col + "；cellvalue:" + rtn);
            return rtn;
        }
    }

}
