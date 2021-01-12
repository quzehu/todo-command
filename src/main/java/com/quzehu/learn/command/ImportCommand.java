package com.quzehu.learn.command;

import com.alibaba.excel.EasyExcel;
import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.Options;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.FileUtils;
import com.quzehu.learn.listener.ExcelReadListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入文件命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.ImportCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/12 10:40
 * @Version 1.0
 */
public class ImportCommand extends AbstractCommand implements Command, Print, IfOrElse {

    private final TodoReceiver todoReceiver;

    private final TodoConfig todoConfig;

    static {
        List<Options> optionsList = new ArrayList<>();
        optionsList.add(new Options(StringConstant.IMPORT_COMMAND, "-f",
                "从文件中导入待办事项"));
        getOptionsMap().put(StringConstant.IMPORT_COMMAND, optionsList);
    }

    public ImportCommand(TodoReceiver todoReceiver, TodoConfig todoConfig) {
        this.todoReceiver = todoReceiver;
        this.todoConfig = todoConfig;
    }

    @Override
    public void execute() {
        println(StringConstant.EXPORT_ERROR_PARAM_LENGTH_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String... args) throws IllegalArgumentException {
        if (args.length == 2) {
            if ("-f".equals(args[0])) {
                String fileName = args[1] + ".xlsx";
                // 先清空
                todoReceiver.clearList();
                try {
                    File importFile = FileUtils.createExistsFile(todoConfig.getImportPath(), fileName);
                    EasyExcel.read(importFile, TodoItem.class,
                            new ExcelReadListener(todoReceiver)).sheet(0).doRead();
                    println(StringConstant.IMPORT_SUCCESS_PROMPT_CONSOLE);
                }catch (IOException e) {
                    println(e.getMessage());
                }
            } else {
                exceptionAction();
            }
        } else {
            exceptionAction();
        }

    }

    private void exceptionAction() {
        throw new IllegalArgumentException(StringConstant.EXPORT_ERROR_PARAM_LENGTH_PROMPT_CONSOLE);
    }
}
