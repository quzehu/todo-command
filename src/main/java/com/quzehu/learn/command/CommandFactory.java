package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.config.UserConfig;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.interceptor.CommandInterceptor;
import com.quzehu.learn.receiver.LocalFileMoreTodoReceiver;
import com.quzehu.learn.receiver.LocalUserReceiver;
import com.quzehu.learn.receiver.MemoryMoreTodoReceiver;
import com.quzehu.learn.receiver.MysqlMoreTodoReceiver;
import com.quzehu.learn.utils.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 命令工厂，用于生产各种命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.invoker.InvokerFactory
 * @Author Qu.ZeHu
 * @Date 2021/1/3 18:47
 * @Version 1.0
 */

public class CommandFactory {

    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

    public CommandFactory() {

        UserConfig userConfig = SpringContextHolder.getBean(UserConfig.class);
        TodoConfig todoConfig = SpringContextHolder.getBean(TodoConfig.class);

        TodoReceiver todoReceiver = chooseReceiver(todoConfig.getPersistence());
        CommandInterceptor proxy = new CommandInterceptor();
        COMMAND_MAP.put(StringConstant.ADD_COMMAND, proxy.createProxy(new AddCommand(todoReceiver)));
        COMMAND_MAP.put(StringConstant.DONE_COMMAND, proxy.createProxy(new DoneCommand(todoReceiver)));

        COMMAND_MAP.put(StringConstant.LIST_COMMAND, proxy.createProxy(new ListCommand(todoReceiver)));

        UserReceiver userReceiver = SpringContextHolder.getBean(LocalUserReceiver.class);
        COMMAND_MAP.put(StringConstant.LOGIN_COMMAND, new LoginCommand(userReceiver));
        COMMAND_MAP.put(StringConstant.PASSWORD_COMMAND, new PasswordCommand(userConfig));
        COMMAND_MAP.put(StringConstant.LOGOUT_COMMAND, proxy.createProxy(new LogoutCommand()));
        COMMAND_MAP.put(StringConstant.HELP_COMMAND, new HelpCommand());
    }

    private static TodoReceiver chooseReceiver(String strategy) {
        TodoReceiver todoReceiver;
        switch (strategy){
            case "localFile":
                todoReceiver = SpringContextHolder.getBean(LocalFileMoreTodoReceiver.class);
                break;
            case "datasource":
                todoReceiver = SpringContextHolder.getBean(MysqlMoreTodoReceiver.class);
                break;
            case "memory":
                todoReceiver = SpringContextHolder.getBean(MemoryMoreTodoReceiver.class);
                break;
            default:
                throw new IllegalArgumentException("配置文件参数错误");
        }
        return todoReceiver;
    }


    public Command createCommand(String commandStr) {
        Optional.ofNullable(commandStr)
                .orElseThrow(() -> new IllegalArgumentException(StringConstant.TODO_ERROR_CONSOLE));
        Command command = COMMAND_MAP.get(commandStr.toLowerCase());
        Optional.ofNullable(command)
                .orElseThrow(() -> new IllegalArgumentException(StringConstant.TODO_ERROR_NOT_EXIST_CONSOLE));
        return command;
    }


    private static final CommandFactory intance = new CommandFactory();

    public static CommandFactory getInstance() {
        return intance;
    }

    private static class Holder {
        public static CommandFactory instance = new CommandFactory();
    }


}
