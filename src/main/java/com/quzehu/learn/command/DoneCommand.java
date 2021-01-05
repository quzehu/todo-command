package com.quzehu.learn.command;

import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.receiver.Receiver;

/**
 * 完成待办事项
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.DoneCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/3 17:20
 * @Version 1.0
 */
public class DoneCommand implements Command {

    private final Receiver receiver;

    public DoneCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        println("Please input 'todo done <itemIndex>', itemIndex must be of integer type.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("The args length must be one!");
        }
        // Todo 参数校验是否为数字
        int index = Integer.parseInt(args[0]);
        if (receiver.done(index)) {
            println(StringFormatTemplate.DONE_AFTER_FORMAT_CONSOLE, index);
        }

    }
}
