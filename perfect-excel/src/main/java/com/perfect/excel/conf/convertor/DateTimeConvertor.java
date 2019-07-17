package com.perfect.excel.conf.convertor;

import com.perfect.common.utils.DateTimeUtil;
import com.perfect.excel.readwrite.JxlExcelException;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by gordian on 2016/1/10.
 */
public class DateTimeConvertor extends BaseConvertor {

    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String doConvert(Object input) {
        return DateFormatUtils.format((Date) input, DATETIME);
    }

    @Override
    public Object doConvertToType(String input) {
        try {
            return DateTimeUtil.parseDate(input, new String[]{DATETIME});
        } catch (ParseException e) {
            throw new JxlExcelException(e);
        }
    }
}
