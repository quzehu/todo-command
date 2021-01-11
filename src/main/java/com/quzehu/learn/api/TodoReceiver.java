package com.quzehu.learn.api;

import com.quzehu.learn.model.TodoItem;

import java.util.List;

/**
 * 接受待办事项命令的执行者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.Receiver
 * @Author Qu.ZeHu
 * @Date 2021/1/3 15:40
 * @Version 1.0
 */
public interface TodoReceiver {

    /**
     * 没有参数的列表
     * @return 列表
     */
    List<TodoItem> list();

    /***
     * 带参数的列表
     * @param args 参数
     * @return 列表
     */
    List<TodoItem> list(String ...args);

    /**
     * 得到 index 索引下的待办事项
     * @param index 索引
     * @return 待办事项
     */
    TodoItem valueOf(int index);

    /**
     * 完成 index 索引下的待办事项
     * @param index 索引
     * @return 成功 true 失败 false
     */
    boolean done(int index);

    /**
     * 添加一个待办事项
     * @param text 文本
     * @return 索引
     */
    int add(String text);

    /**
     * 导出文件
     * @param args 参数
     */
    void exportFile(String ...args);

    /**
     * 导入文件
     * @param args 参数
     */
    void importFile(String ...args);


}
