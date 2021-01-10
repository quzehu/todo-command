package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.model.Options;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.AbstractCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/10 20:19
 * @Version 1.0
 */
public abstract class AbstractCommand implements Command, Print {

    /**
     * 命令的参数集合
     */
    private static final Map<String, List<Options>> optionsMap = new HashMap<>();

    public static Map<String, List<Options>> getOptionsMap() {
        return optionsMap;
    }

    public static List<Options> getOptionsByKey(String key) {
        return optionsMap.get(key);
    }

    protected void printAllOptionsAction(String commandName) {
        List<Options> optionsList = getOptionsMap().get(commandName);
        for (Options options : optionsList) {
            print(options.toString());
        }
    }

}
