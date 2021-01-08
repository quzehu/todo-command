package com.quzehu.learn.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析工具类
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.utils.PatternUtils
 * @Author Qu.ZeHu
 * @Date 2021/1/8 14:09
 * @Version 1.0
 */
public class PatternUtils {

   private static final Pattern PATTERN = Pattern.compile("[0-9]*");

    public static boolean isNumeric(String str) {
        Matcher isNum = PATTERN.matcher(str);
        return isNum.matches();
    }


}
