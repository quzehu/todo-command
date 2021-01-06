package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.api.TodoReceiver;

/**
 * 完成待办事项
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.DoneCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/3 17:20
 * @Version 1.0
 */
public class DoneCommand implements Command, Print {

    private final TodoReceiver todoReceiver;

    public DoneCommand(TodoReceiver todoReceiver) {
        this.todoReceiver = todoReceiver;
    }

    @Override
    public void execute() {
        println(StringConstant.DONE_ERROR_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("The args length must be one!");
        }
        // Todo 参数校验是否为数字
        int index = Integer.parseInt(args[0]);
        if (todoReceiver.done(index)) {
            println(StringFormatTemplate.DONE_AFTER_FORMAT_CONSOLE, index);
        }

    }
}
