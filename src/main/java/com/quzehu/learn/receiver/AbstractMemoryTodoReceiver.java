package com.quzehu.learn.receiver;

import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.model.TodoItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象的内存接收者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.AbstractMemoryReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/5 14:54
 * @Version 1.0
 */

public abstract class AbstractMemoryTodoReceiver implements TodoReceiver {

    protected final List<TodoItem> items = new ArrayList<>();

    protected final Map<Integer, List<TodoItem>> userItemMap = new HashMap<>();

    public void addAll(List<TodoItem> todoItems) {
        items.addAll(todoItems);
    }

    public List<TodoItem> getItems() {
        return items;
    }

    protected void check(int index) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Currently container no elements.");
        }
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException("The index is a invalid parameter.");
        }
    }
}