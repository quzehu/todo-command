package com.quzehu.learn.receiver;

import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.UserSessionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


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
@Lazy
public class MemoryMoreTodoReceiver extends AbstractMemoryTodoReceiver {

    @Override
    public List<TodoItem> list() {
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems = getTodoListByKey(userId);
        if (todoItems.isEmpty()) {
            return todoItems;
        }
        return filterNotDoneTodoList(todoItems);
    }

    @Override
    public List<TodoItem> list(String ...args) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems = getTodoListByKey(userId);
        switch (args[0]) {
            case "--all":
                return todoItems;
            case "--done":
                return filterDoneTodoList(todoItems);
            default:
                throw new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE);
        }
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
