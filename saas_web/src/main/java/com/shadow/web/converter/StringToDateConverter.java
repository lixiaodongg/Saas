package com.shadow.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转换成日期
 */
public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String dataStr) {
        try {
            if (StringUtils.isEmpty(dataStr)) {
                return null;
            }
            return new SimpleDateFormat("yyyy-MM-dd").parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
