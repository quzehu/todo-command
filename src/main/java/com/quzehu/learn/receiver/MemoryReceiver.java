package com.quzehu.learn.receiver;

import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.item.TodoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用内存作为待办事项的存储容器
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.MemoryReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/3 18:07
 * @Version 1.0
 */
public class MemoryReceiver implements Receiver {

    private final List<TodoItem> items = new ArrayList<>();

    @Override
    public List<TodoItem> list() {
        if (items.isEmpty()) {
            return items;
        }
        return items.stream()
                .filter(item -> ItemStatusEnum.NOT_DONE.equals(item.getStatusEnum()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> list(String... args) {
        for (String arg : args) {
            switch (arg) {
                case "--all":
                    return items;
                case "--done":
                    return items.stream()
                            .filter(item -> ItemStatusEnum.DONE.equals(item.getStatusEnum()))
                            .collect(Collectors.toList());
                default:
                    throw new IllegalArgumentException("The args is a invalid parameter.");
            }
        }
        return null;
    }


    @Override
    public TodoItem valueOf(int index) {
        // 校验参数
        check(index - 1);

        return items.get(index - 1);
    }

    @Override
    public boolean done(int index) {
        // 校验
        check(index - 1);
        TodoItem todoItem = items.get(index - 1);
        todoItem.setStatusEnum(ItemStatusEnum.DONE);
        return true;
    }

    @Override
    public int add(String text) {
        int newIndex = items.size() + 1;
        items.add(new TodoItem(newIndex, text));
        return newIndex;
    }

    private void check(int index) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Currently container no elements.");
        }
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException("The index is a invalid parameter.");
        }
    }
}
