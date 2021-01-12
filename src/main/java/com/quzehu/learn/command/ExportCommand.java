package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.Options;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出文件命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.ExportCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/11 14:22
 * @Version 1.0
 */
public class ExportCommand extends AbstractCommand implements Command, Print, IfOrElse {

    private final TodoReceiver todoReceiver;


    static {
        List<Options> optionsList = new ArrayList<>();
        optionsList.add(new Options(StringConstant.EXPORT_COMMAND, "-a", "导出所有的待办事项"));
        optionsList.add(new Options(StringConstant.EXPORT_COMMAND, "-d", "导出已经完成的待办事项"));
        optionsList.add(new Options(StringConstant.EXPORT_COMMAND, "-n", "导出没有完成的待办事项"));
        getOptionsMap().put(StringConstant.EXPORT_COMMAND, optionsList);
    }

    public ExportCommand(TodoReceiver todoReceiver) {
        this.todoReceiver = todoReceiver;
    }



    @Override
    public void execute() {
        println(StringConstant.EXPORT_ERROR_PARAM_LENGTH_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String... args) throws IllegalArgumentException {

        ifPresentOrElse(args.length, 2, args, todoReceiver::exportFile, this::exceptionAction);
        println(StringConstant.EXPORT_SUCCESS_PROMPT_CONSOLE);
    }

    private void exceptionAction() {
        throw new IllegalArgumentException(StringConstant.EXPORT_ERROR_PARAM_LENGTH_PROMPT_CONSOLE);
    }
}
