package com.quzehu.learn.command;

import com.quzehu.learn.constant.CommandEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.Options;

import java.util.ArrayList;
import java.util.List;
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
public class HelpCommand extends AbstractCommand {

    static {
        List<Options> optionsList = new ArrayList<>();
        optionsList.add(new Options(StringConstant.HELP_COMMAND, "--add", "获取添加命令的帮助"));
        optionsList.add(new Options(StringConstant.HELP_COMMAND, "--done", "获取该完成命令的帮助"));
        optionsList.add(new Options(StringConstant.HELP_COMMAND, "--list", "获取该查看列表命令的帮助"));
        optionsList.add(new Options(StringConstant.HELP_COMMAND, "--login", "获取该登录命令的帮助"));
        optionsList.add(new Options(StringConstant.HELP_COMMAND, "--logout", "获取登出命令的帮助"));
        optionsList.add(new Options(StringConstant.HELP_COMMAND, "--help", "获取帮助命令的帮助"));
        getOptionsMap().put(StringConstant.HELP_COMMAND, optionsList);
    }

    @Override
    public void execute() {
        CommandEnum[] values = CommandEnum.values();
        for (CommandEnum e : values) {
            String description = e.getDescription();
            if (description.contains("%s")) {
              formatDescription(e);
            }
            println(e.toString());
        }
    }

    private void formatDescription(CommandEnum e) {
        List<Options> optionsByKey = getOptionsByKey(e.getName());
        StringBuilder stringBuilder = new StringBuilder();
        for (Options options: optionsByKey) {
            stringBuilder.append(options.toString());
        }
        String format = String.format(e.getDescription(), stringBuilder.toString());
        e.setDescription(format);
    }


    @Override
    public void execute(String... args) throws IllegalArgumentException {
        for (String arg : args) {
            arg = arg.replaceAll("--", "");
            Optional<CommandEnum> optional = Optional.ofNullable(CommandEnum.valueOfByName(arg));
            optional.orElseThrow(() ->
                    new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE));
            CommandEnum commandEnum = optional.get();
            if (commandEnum.getDescription().contains("%s")) {
                formatDescription(optional.get());
            }
            println(commandEnum.toString());
        }
    }
}
