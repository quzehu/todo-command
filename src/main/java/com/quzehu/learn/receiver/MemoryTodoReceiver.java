package com.quzehu.learn.receiver;

import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.TodoItem;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

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
@Deprecated
public class MemoryTodoReceiver extends AbstractMemoryTodoReceiver {

    @Override
    public List<TodoItem> list() {
        if (getItems().isEmpty()) {
            return getItems();
        }
        return filterNotDoneTodoList(getItems());
    }

    @Override
    public List<TodoItem> list(String ...args) {
        switch (args[0]) {
            case "--all":
                return getItems();
            case "--done":
                return filterDoneTodoList(getItems());
            default:
                throw new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE);
        }
    }


    @Override
    public TodoItem valueOf(int index) {
        // 校验参数
        check(index - 1);
        return getItems().get(index - 1);
    }

    @Override
    public boolean done(int index) {
        // 校验
        check(index - 1);
        TodoItem todoItem = getItems().get(index - 1);
        todoItem.setStatus(ItemStatusEnum.DONE.getStatus());
        return true;
    }

    @Override
    public int add(String text) {
        int newIndex = getItems().size() + 1;
        getItems().add(new TodoItem(newIndex, text));
        return newIndex;
    }


    @Override
    public void exportFile(String... args) {

    }

    @Override
    public void importFile(String... args) {

    }
}
