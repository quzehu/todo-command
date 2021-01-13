package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.receiver.LocalFileMoreTodoReceiver;
import com.quzehu.learn.receiver.MysqlMoreTodoReceiver;
import com.quzehu.learn.utils.UserSessionUtils;

import java.io.File;
import java.util.List;

/**
 * 初始化数据库命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.InitDBCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/13 22:23
 * @Version 1.0
 */
public class InitDBCommand implements Command, Print {

    private final TodoConfig todoConfig;

    private final LocalFileMoreTodoReceiver fileReceiver;

    private final MysqlMoreTodoReceiver mysqlReceiver;

    public InitDBCommand(TodoConfig todoConfig,
                         LocalFileMoreTodoReceiver fileReceiver,
                         MysqlMoreTodoReceiver mysqlReceiver) {
        this.todoConfig = todoConfig;
        this.fileReceiver = fileReceiver;
        this.mysqlReceiver = mysqlReceiver;
    }

    @Override
    public void execute(String... args) throws IllegalArgumentException {
        println(StringConstant.INIT_DB_ERROR_PARAM_LENGTH_PROMPT_CONSOLE);
    }


    @Override
    public void execute() {
        if (exists(getUserFile(todoConfig.getBasePath(), todoConfig.getFileName()))) {
            List<TodoItem> todoItems = fileReceiver.readListFromFile(convertFileName(todoConfig.getFileName()));
            mysqlReceiver.clearList();
            mysqlReceiver.importFile(todoItems);
            println(StringConstant.INIT_DB_SUCCESS_PROMPT_CONSOLE);
        } else {
            println(StringConstant.INIT_DB_ERROR_FILE_NOT_EXISTS_PROMPT_CONSOLE);
        }
    }

    private File getUserFile(String filePath, String fileName) {
        return new File(filePath, convertFileName(fileName));
    }

    private String convertFileName(String fileName) {
        String userName = UserSessionUtils.getUserNameBySession();
        return String.format(StringFormatTemplate.USER_FILE_NAME_FORMAT,
                userName, fileName);
    }

    private boolean exists(File file) {
        return file.exists();
    }



}
