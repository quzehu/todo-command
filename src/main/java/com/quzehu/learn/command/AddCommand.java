package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.api.TodoReceiver;

/**
 * 待办事项添加命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.AddCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/3 17:04
 * @Version 1.0
 */
public class AddCommand implements Command, Print {

    private final TodoReceiver todoReceiver;

    public AddCommand(TodoReceiver todoReceiver) {
        this.todoReceiver = todoReceiver;
    }


    @Override
    public void execute() {
        println("Please input 'todo add <item>' item is a nothing String!");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("The args length must be one!");
        }
        int index = todoReceiver.add(args[0]);
        TodoItem todoItem = todoReceiver.valueOf(index);
        println(todoItem.toString());
        println(StringFormatTemplate.ADD_AFTER_FORMAT_CONSOLE, index);
    }
}
