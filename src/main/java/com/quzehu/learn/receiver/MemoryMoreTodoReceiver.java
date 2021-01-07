package com.quzehu.learn.receiver;

import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;
import com.quzehu.learn.utils.UserSessionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 支持用户信息的内存接收者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.UserMemoryTodoReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/6 14:47
 * @Version 1.0
 */
@Component
public class MemoryMoreTodoReceiver extends AbstractMemoryTodoReceiver {

    @Override
    public List<TodoItem> list() {
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems = getTodoListByKey(userId);
        if (todoItems.isEmpty()) {
            return todoItems;
        }
        return todoItems.stream()
                .filter(item -> ItemStatusEnum.NOT_DONE.getStatus().equals(item.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> list(String... args) {
        // Todo 重构
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems = getTodoListByKey(userId);
        for (String arg : args) {
            switch (arg) {
                case "--all":
                    return todoItems;
                case "--done":
                    return todoItems.stream()
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
        // 校验
        Integer userId = UserSessionUtils.getUserIdBySession();
        check(userId, index -1);
        return getTodoListByKey(userId).get(index - 1);
    }

    @Override
    public boolean done(int index) {
        // 校验
        Integer userId = UserSessionUtils.getUserIdBySession();
        check(userId, index -1);

        TodoItem todoItem = getTodoListByKey(userId).get(index - 1);
        todoItem.setStatus(ItemStatusEnum.DONE.getStatus());
        return true;
    }

    @Override
    public int add(String text) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        int newIndex = getTodoListByKey(userId).size() + 1;
        addMapByKey(userId, new TodoItem(newIndex, text, userId));
        return newIndex;
    }



}
