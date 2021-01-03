package com.quzehu.learn.receiver;

import com.quzehu.learn.item.TodoItem;

import java.util.List;

/**
 * 接受命令的 执行者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.Receiver
 * @Author Qu.ZeHu
 * @Date 2021/1/3 15:40
 * @Version 1.0
 */
public interface Receiver {

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
    List<TodoItem> list(String... args);

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
     * @return 索引
     */
    int add(String text);

}
