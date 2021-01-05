package com.quzehu.learn.command;

/**
 * 命令接口
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.Command
 * @Author Qu.ZeHu
 * @Date 2021/1/3 15:37
 * @Version 1.0
 */
public interface Command {
    /**
     * 执行命令
     */
    void execute();

    /**
     * 带参数的执行命令
     * @param args 参数
     */
    void execute(String... args) throws IllegalArgumentException;

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

}
