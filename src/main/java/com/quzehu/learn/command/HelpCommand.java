package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.CommandEnum;
import com.quzehu.learn.constant.StringConstant;

import java.util.Optional;

/**
 * 帮助命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.HelpCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/10 13:02
 * @Version 1.0
 */
public class HelpCommand implements Command, Print {

    @Override
    public void execute() {
        CommandEnum[] values = CommandEnum.values();
        for (CommandEnum e : values) {
            println(e.toString());
        }
    }

    @Override
    public void execute(String... args) throws IllegalArgumentException {
        for (String arg : args) {
            arg = arg.replaceAll("--", "");
            Optional<CommandEnum> optional = Optional.ofNullable(CommandEnum.valueOfByName(arg));
            optional.orElseThrow(() ->
                    new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE));
            println(optional.get().toString());
        }
    }
}
