package com.quzehu.learn.receiver;

import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.model.TodoItem;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
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
@Component
@Lazy
public class MemoryTodoReceiver extends AbstractMemoryTodoReceiver {

    @Override
    public List<TodoItem> list() {
        if (items.isEmpty()) {
            return items;
        }
        return items.stream()
                .filter(item -> ItemStatusEnum.NOT_DONE.getStatus().equals(item.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> list(String... args) {
        // Todo 重构
        for (String arg : args) {
            switch (arg) {
                case "--all":
                    return items;
                case "--done":
                    return items.stream()
                            .filter(item -> ItemStatusEnum.DONE.getStatus().equals(item.getStatus()))
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
        todoItem.setStatus(ItemStatusEnum.DONE.getStatus());
        return true;
    }

    @Override
    public int add(String text) {
        int newIndex = items.size() + 1;
        items.add(new TodoItem(newIndex, text));
        return newIndex;
    }



}
