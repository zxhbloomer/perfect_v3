package com.perfect.excel.export;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.perfect.common.utils.DateTimeUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.perfect.common.annotation.Excel;
import com.perfect.common.annotation.Excels;
import com.perfect.common.exception.BusinessException;
import com.perfect.common.utils.string.StringUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * Excel相关处理
 * 
 * @author zxh
 */
public class ExcelUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public static final String EXCEL_SUFFIX= ".xlsx";

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Object[]> fields;

    /**
     * 实体对象
     */
    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void init(List<T> list, String sheetName, String fileName) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.fileName = fileName;
        createExcelField();
        createWorkbook();
    }

    /**
     * 下载文件
     * @param serverFile 服务器端文件路径
     * @param downLoadFile 下载文件的文件名
     * @throws IOException
     */
    public static void download(String serverFile,String downLoadFile, HttpServletResponse response) throws IOException {
        /** 获取下载文件的路径 */
        File file = new File(serverFile);
        InputStream in = new FileInputStream(serverFile);
        /** 获取输出流 */
        OutputStream out = response.getOutputStream();

        /** 设置导出文件名称 */
        downLoadFile = downLoadFile + "_" + DateTimeUtil.dateTimeNow();
        /** 弹出下载的框filename:提示用户下载的文件名 */
        response.addHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(downLoadFile,"utf-8"));
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        log.debug("获取responseContentType：" + response.getContentType());
         /** 循环读取 */
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = in.read(buff)) > 0) {
            out.write(buff,0,len);
            out.flush();
        }
        //关闭流资源
        in.close();
        out.close();
        //删除服务器端生成的下载文件,减轻服务器的压力
//        File file = new File(serverFile);
        if(file.exists()){
            file.delete();
        }
    }
    /**
     * 对list数据源将其里面的数据导入到excel表单
     * @param exportFileName
     * @param sheetName
     * @param dataList
     * @param response
     */
    public void exportExcel(String exportFileName, String sheetName, List<T> dataList, HttpServletResponse response)
        throws IOException {
        this.init(dataList, sheetName, exportFileName);
        exportExcel(response);
    }

    private void exportExcel(HttpServletResponse response) throws IOException {
        String fileNamePath = doExport();
        /**下载Excel文件*/
        download(fileNamePath, this.fileName, response);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @return 结果
     */
    public String doExport() {
        OutputStream out = null;
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);

                // 产生一行
                Row row = sheet.createRow(0);
                int column = 0;
                // 写入各个字段的列头名称
                for (Object[] os : fields) {
                    Excel excel = (Excel)os[1];
                    this.createCell(excel, row, column++);
                }
                fillExcelData(index, row);
            }
            String tmpFilename = getAbsoluteFile(this.fileName);
            out = new FileOutputStream(tmpFilename);
            wb.write(out);
            return tmpFilename;
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 填充excel数据
     * 
     * @param index 序号
     * @param row 单元格行
     */
    public void fillExcelData(int index, Row row) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        // 写入各条记录,每条记录对应excel表中的一行
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = startNo; i < endNo; i++) {
            row = sheet.createRow(i + 1 - startNo);
            // 得到导出对象.
            T vo = (T)list.get(i);
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field)os[0];
                Excel excel = (Excel)os[1];
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                this.addCell(excel, row, vo, field, column++, cs);
            }
        }
    }

    /**
     * 创建单元格
     */
    public Cell createCell(Excel attr, Row row, int column) {
        // 创建列
        Cell cell = row.createCell(column);
        // 设置列中写入内容为String类型
        cell.setCellType(CellType.STRING);
        // 写入列名
        cell.setCellValue(attr.name());
        CellStyle cellStyle = createStyle(attr, row, column);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 创建表格样式
     */
    public CellStyle createStyle(Excel attr, Row row, int column) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (attr.name().indexOf("注：") >= 0) {
            Font font = wb.createFont();
            font.setColor(HSSFFont.COLOR_RED);
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
            sheet.setColumnWidth(column, 6000);
        } else {
            Font font = wb.createFont();
            // 粗体显示
            font.setBold(true);
            // 选择需要用到的字体格式
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
            // 设置列宽
            sheet.setColumnWidth(column, (int)((attr.width() + 0.72) * 256));
            row.setHeight((short)(attr.height() * 20));
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        // 如果设置了提示信息则鼠标放上去提示.
        if (StringUtil.isNotEmpty(attr.prompt())) {
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // 如果设置了combo属性则本列只能选择不能输入
//        if (attr.combo().length > 0) {
//            // 这里默认设了2-101列只能选择不能输入.
//            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
//        }
        return cellStyle;
    }

    /**
     * 添加单元格
     */
    public Cell addCell(Excel attr, Row row, T vo, Field field, int column, CellStyle cs) {
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight((short)(attr.height() * 20));
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()) {
                // 创建cell
                cell = row.createCell(column);
                cell.setCellStyle(cs);

                // 用于读取对象中的属性
//                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
//                String readConverterExp = attr.readConverterExp();
//                if (StringUtil.isNotEmpty(dateFormat) && StringUtil.isNotNull(value)) {
//                    cell.setCellValue(DateTimeUtil.parseDateToStr(dateFormat, (Date)value));
//                } else if (StringUtil.isNotEmpty(readConverterExp) && StringUtil.isNotNull(value)) {
//                    cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
//                } else {
//                    cell.setCellType(CellType.STRING);
//                    // 如果数据存在就填入,不存在填入空格.
//                    cell.setCellValue(StringUtil.isNull(value) ? attr.defaultValue() : value + attr.suffix());
//                }
            }
        } catch (Exception e) {
            log.error("导出Excel失败{}", e);
        }
        return cell;
    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     * 
     * @param sheet 表单
     * @param promptTitle 提示标题
     * @param promptContent 提示内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
        int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * 
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     * @return 设置好的sheet.
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     * 
     * @param propertyValue 参数值
     * @param converterExp 翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String convertByExp(String propertyValue, String converterExp) throws Exception {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 获取下载路径
     * 
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename) throws IOException {
        //生成UUID唯一标识，以防止文件覆盖
        UUID uuid = UUID.randomUUID();
        File tempFile = TempFile.createTempFile(uuid.toString()+filename, EXCEL_SUFFIX);
        log.debug("生成临时文件，路径为:" + tempFile.getAbsolutePath());

//        String downloadPath = "" + filename;
//        File desc = new File(downloadPath);
//        if (!desc.getParentFile().exists()) {
//            desc.getParentFile().mkdirs();
//        }
        return tempFile.getAbsolutePath();
    }

//    /**
//     * 获取bean中的属性值
//     *
//     * @param vo 实体对象
//     * @param field 字段
//     * @param excel 注解
//     * @return 最终的属性值
//     * @throws Exception
//     */
//    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
//        Object o = field.get(vo);
//        if (StringUtil.isNotEmpty(excel.targetAttr())) {
//            String target = excel.targetAttr();
//            if (target.indexOf(".") > -1) {
//                String[] targets = target.split("[.]");
//                for (String name : targets) {
//                    o = getValue(o, name);
//                }
//            } else {
//                o = getValue(o, target);
//            }
//        }
//        return o;
//    }

    /**
     * 以类的属性的get方法方法形式获取值
     * 
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtil.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = new ArrayList<Object[]>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                putToField(field, field.getAnnotation(Excel.class));
            }

            // 多注解
            if (field.isAnnotationPresent(Excels.class)) {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel excel : excels) {
                    putToField(field, excel);
                }
            }
        }
    }

    /**
     * 放到字段集合中
     */
    private void putToField(Field field, Excel attr) {
        if (attr != null ) {
            this.fields.add(new Object[] {field, attr});
        }
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

    /**
     * 创建工作表
     * 
     * @param sheetNo，指定Sheet名称
     * @param sheetNo sheet数量
     * @param index 序号
     */
    public void createSheet(double sheetNo, int index) {
        this.sheet = wb.createSheet();
        // 设置工作表的名称.
        if (sheetNo == 0) {
            wb.setSheetName(index, sheetName);
        } else {
            wb.setSheetName(index, sheetName + index);
        }
    }

    /**
     * 获取单元格值
     * 
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return row;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        val = DateUtil.getJavaDate((Double)val); // POI Excel 日期格式转换
                    } else {
                        if ((Double)val % 1 > 0) {
                            val = new DecimalFormat("0.00").format(val);
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }

            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }
}