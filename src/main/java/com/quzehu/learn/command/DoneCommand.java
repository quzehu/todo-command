package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.utils.PatternUtils;

/**
 * 完成待办事项命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.DoneCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/3 17:20
 * @Version 1.0
 */
public class DoneCommand implements Command, Print, IfOrElse {

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
        // 支持多参数
        for (String arg : args) {
            boolean isNumber = PatternUtils.isNumeric(arg);
            ifPresentOrElse(isNumber, arg, this::doneAction, this::errorAction);
        }
    }


    private void errorAction() {
        println(StringConstant.DONE_ERROR_PARAM_PROMPT_CONSOLE);
    }

    private void doneAction(String arg) {
        int index = Integer.parseInt(arg);
        if (todoReceiver.done(index)) {
            println(StringFormatTemplate.DONE_AFTER_FORMAT_CONSOLE, index);
        }
    }


}
