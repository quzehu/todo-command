package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.receiver.LocalFileTodoReceiver;
import com.quzehu.learn.receiver.LocalUserReceiver;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.utils.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用者工厂 用于生产各种命令
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
        TodoReceiver todoReceiver = SpringContextHolder.getBean(LocalFileTodoReceiver.class);
        UserReceiver userReceiver = SpringContextHolder.getBean(LocalUserReceiver.class);
        COMMAND_MAP.put(StringConstant.ADD_COMMAND, new AddCommand(todoReceiver));
        COMMAND_MAP.put(StringConstant.DONE_COMMAND, new DoneCommand(todoReceiver));
        COMMAND_MAP.put(StringConstant.LIST_COMMAND, new ListCommand(todoReceiver));
        COMMAND_MAP.put(StringConstant.LOGIN_COMMAND, new LoginCommand(userReceiver));
        COMMAND_MAP.put(StringConstant.PASSWORD_COMMAND, SpringContextHolder.getBean(PasswordCommand.class));
    }

    public Command createCommand(String commandStr) {
        if (commandStr == null || commandStr.isEmpty()) {
            throw new IllegalArgumentException("command is wrong");
        }
        Command command = COMMAND_MAP.get(commandStr.toLowerCase());
        if (command == null) {
            throw new IllegalArgumentException("command is wrong");
        }
        return command;
    }

    public static CommandFactory getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        public static CommandFactory instance = new CommandFactory();
    }



}
