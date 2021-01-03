package com.quzehu.learn.command;

import com.quzehu.learn.command.AddCommand;
import com.quzehu.learn.command.Command;
import com.quzehu.learn.command.DoneCommand;
import com.quzehu.learn.command.ListCommand;
import com.quzehu.learn.receiver.MemoryReceiver;
import com.quzehu.learn.receiver.Receiver;

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

    private static final Map<String, Command> commands = new HashMap<>();

    static {
        Receiver receiver = new MemoryReceiver();
        commands.put("add", new AddCommand(receiver));
        commands.put("done", new DoneCommand(receiver));
        commands.put("list", new ListCommand(receiver));
    }

    public static Command createCommand(String commandStr) {
        if (commandStr == null || commandStr.isEmpty()) {
            throw new IllegalArgumentException("command is wrong");
        }
        Command command = commands.get(commandStr.toLowerCase());
        if (command == null) {
            throw new IllegalArgumentException("command is wrong");
        }
        return command;
    }





}
