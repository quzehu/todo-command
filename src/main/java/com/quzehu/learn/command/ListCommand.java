package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.api.TodoReceiver;

import java.util.List;

/**
 * 列表命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.ListCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/3 15:38
 * @Version 1.0
 */
public class ListCommand implements Command, Print, IfOrElse {

    private final TodoReceiver todoReceiver;

    public ListCommand(TodoReceiver todoReceiver) {
        this.todoReceiver = todoReceiver;
    }

    @Override
    public void execute() {
        List<TodoItem> todoItems = todoReceiver.list();
        todoItems.forEach(item -> println(item.toString()));
        println(StringFormatTemplate.LIST_AFTER_FORMAT_CONSOLE, todoItems.size());
    }

    @Override
    public void execute(String... args) {
        // 参数错误
        orElse(args.length, 1,
        () -> {throw new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_LENGTH_PROMPT_CONSOLE);});

        List<TodoItem> todoItems = todoReceiver.list(args);

        todoItems.forEach(todoItem -> println(todoItem.toString()));

        long doneSize = todoItems.stream().filter(item -> ItemStatusEnum.DONE.getStatus()
                        .equals(item.getStatus())).count();

        println(StringFormatTemplate.LIST_ALL_AFTER_FORMAT_CONSOLE, todoItems.size(), doneSize);
    }
}
