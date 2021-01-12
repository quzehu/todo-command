package com.quzehu.learn.receiver;

import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.model.TodoItem;

import java.util.List;

/**
 * 适配器
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.AdapterTodoReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/12 14:26
 * @Version 1.0
 */
public class AdapterTodoReceiver implements TodoReceiver {
    @Override
    public List<TodoItem> list() {
        return null;
    }

    @Override
    public List<TodoItem> list(String... args) {
        return null;
    }

    @Override
    public TodoItem valueOf(int index) {
        return null;
    }

    @Override
    public boolean done(int index) {
        return false;
    }

    @Override
    public int add(String text) {
        return 0;
    }

    @Override
    public void exportFile(String... args) {

    }

    @Override
    public void importFile(List<TodoItem> todoItems) {

    }

    @Override
    public void clearList() {

    }
}
