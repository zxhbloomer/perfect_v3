package com.perfect.excel.upload;

import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.utils.string.StringUtil;
import com.perfect.excel.bean.importconfig.template.ExcelTemplate;
import com.perfect.excel.bean.importconfig.template.data.DataCol;
import com.perfect.excel.bean.importconfig.template.title.DummyTitleCol;
import com.perfect.excel.bean.importconfig.template.title.TitleCol;
import com.perfect.excel.bean.importconfig.template.title.TitleRow;
import com.perfect.excel.conf.convertor.ConvertorUtil;
import com.perfect.excel.conf.validator.ColValidateResult;
import com.perfect.excel.conf.validator.RowValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * excel导入类
 * 
 * @author zxh
 */
@Slf4j
public class PerfectExcelReader extends PerfectExcelBase {

    private InputStream is;
    private List<RowValidateResult> rowValidateResults = new ArrayList<RowValidateResult>();

    /**
     * true:xlsx,false:xls
     */
    private boolean xlsOrXlsx;
    /**
     * Office 2003 ，xls:HSSFWorkbook
     * Office 2007 ，xls:XSSFWorkbook
     */
    Workbook wb ;

    /**
     * 读取文件
     * 
     * @param excelFile
     * @throws FileNotFoundException
     */
    public PerfectExcelReader(File excelFile, ExcelTemplate et) throws Exception {
        this(new FileInputStream(excelFile), et);
    }

    /**
     * 读取流
     * 
     * @param is
     */
    public PerfectExcelReader(InputStream is, ExcelTemplate et) {
        this.is = is;
        et.initValidator();
        super.setExcelTemplate(et);
    }

    /**
     * 关闭对象
     */
    public void closeAll(){
        try {
            wb.close();
        } catch (IOException e) {
        }
        try {
            is.close();
        } catch (IOException e) {
        }
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
     * 获取包含错误的excel
     * @return
     * @throws IOException
     */
    public File getValidateResultsInFile(String fileName) throws IOException {
        //生成UUID唯一标识，以防止文件覆盖
        UUID uuid = UUID.randomUUID();
        Path tempPath = null;
        OutputStream fos = null;
        File file = null;
        try {
            tempPath = Files.createTempDirectory("ExcelError");
            file = new File(tempPath.toString(), fileName);
            fos = new FileOutputStream(file);
            // ws => outputstream
            if(xlsOrXlsx){
                wb.write(fos);
            }
        } catch (IOException e) {
            throw new PerfectExcelException(e);
        } catch (Exception e) {
            throw new PerfectExcelException(e);
        } finally {
            if(fos != null){
                fos.close();
            }
        }

        return file;
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
                    throw new PerfectExcelException(e);
                } catch (IllegalAccessException e) {
                    throw new PerfectExcelException(e);
                }
            }

            @Override
            protected void setColData(T rowData, DataCol dataCol, Object colDataVal) {
                try {
                    BeanUtils.setProperty(rowData, dataCol.getName(), colDataVal);
                } catch (IllegalAccessException e) {
                    throw new PerfectExcelException(e);
                } catch (InvocationTargetException e) {
                    throw new PerfectExcelException(e);
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
            // office 2003
            xlsOrXlsx = false;
            wb = new HSSFWorkbook(is);
        } else {
            // office 2007
            xlsOrXlsx = true;
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet =  wb.getSheetAt(0);
        readPolicy.checkTemplateTitles(sheet);
        return readPolicy.readDatasFromSheet(sheet);
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
        List<T> readDatasFromSheet(Sheet sheet) {
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
                    setErrorCellValue(row, dataCols.size() + 1, sheet, rowValidateResult.getErrors(excelTemplate.getTitleRows()));
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
            if (sheet.getRow(0).getPhysicalNumberOfCells() != excelTemplate.getColSize()) {
                throw new PerfectExcelException(String.format("读取的excel与模板不匹配：期望%s列，实际为%s列", excelTemplate.getColSize(),
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
                throw new PerfectExcelException("读取的excel与模板不匹配：" + errorMsg);
            }
        }

        /**
         * 设置单元格中的值
         * 
         * @return
         */
        private void setErrorCellValue(int rowId, int col, Sheet sheet, String error) {
            Font font = wb.createFont();
            font.setFontName("微软雅黑");
            font.setColor(Font.COLOR_RED);
            font.setBold(true);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFont(font);

            Row row = sheet.getRow(rowId);
            Row rowHead = sheet.getRow(0);
            // 创建头部head
            Cell cellHead = rowHead.createCell(col);
            cellHead.setCellStyle(cellStyle);
            cellHead.setCellValue("导入错误信息");
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
        String getCellValue(int rowId, int col, Sheet sheet) {
            Row row = sheet.getRow(rowId);
            Cell cell = row.getCell(col);

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
