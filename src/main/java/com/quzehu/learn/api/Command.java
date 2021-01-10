package com.quzehu.learn.api;

/**
 * 命令接口
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.api.Command
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

}
