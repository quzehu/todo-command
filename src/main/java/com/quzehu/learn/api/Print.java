package com.quzehu.learn.api;

/**
 * 打印方式接口
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.api.Print
 * @Author Qu.ZeHu
 * @Date 2021/1/5 20:20
 * @Version 1.0
 */
public interface Print {
    /**
     * 打印带有格式化的信息
     * @param format 模版
     * @param args 参数
     */
    default void println(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    /**
     * 打印文本内容
     * @param text 文本内容
     */
    default void println(String text) {
        System.out.println(text);
    }

    /**
     * 不换行打印
     * @param text
     */
    default void print(String text) {
        System.out.print(text);
    }
}
