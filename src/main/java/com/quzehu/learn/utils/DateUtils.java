package com.quzehu.learn.utils;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DATEFORMATLONG14 = "yyyyMMddHHmmss";

    public static final String DATEFORMATLONG19 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATEFORMATSHORT10 = "yyyy-MM-dd";


    public static Date getCurrentDate() {
        return new Date();
    }

    private static String getDate2String(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat.format(date);
    }

    public static String getDate2LongString(Date date) {
        return getDate2String(DATEFORMATLONG19, date);
    }

    public static String getDate2ShortString10(Date date) {
        return getDate2String(DATEFORMATSHORT10, date);
    }

    public static String getDate2LongString14(Date date) {
        return getDate2String(DATEFORMATLONG14, date);
    }

    private static Date getString2Date(String format, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        ParsePosition parseposition = new ParsePosition(0);
        return simpleDateFormat.parse(str, parseposition);
    }

    public static Date getString2LongDate(String str) {
        return getString2Date(DATEFORMATLONG19, str);
    }

    public static Date getString2ShortDate10(String str) {
        return getString2Date(DATEFORMATSHORT10, str);
    }


}
