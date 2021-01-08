package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringConstant;
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
        println(StringConstant.ADD_ERROR_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String... args) {
        // 支持多文本添加
        for (String arg : args) {
            int index = todoReceiver.add(arg);
            TodoItem todoItem = todoReceiver.valueOf(index);
            println(todoItem.toString());
            println(StringFormatTemplate.ADD_AFTER_FORMAT_CONSOLE, index);
        }
    }
}
