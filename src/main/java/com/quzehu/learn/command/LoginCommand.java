package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.Parameter;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;
import com.quzehu.learn.api.UserReceiver;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.LoginCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/5 21:11
 * @Version 1.0
 */
public class LoginCommand implements Command, Print {

    private final UserReceiver userReceiver;

    public LoginCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
        this.parameters = new ArrayList<>();
        parameters.add(new Parameter(StringConstant.LOGIN_COMMAND, "-u", ""));
    }

    /**
     * 所有登录命令的参数集合
     */
    private List<Parameter> parameters;


    @Override
    public void execute() {
        print(StringConstant.LOGIN_ERROR_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String... args) throws IllegalArgumentException {
        // Todo 需要重构
        if (args.length == 2) {
            if ("-u".equals(args[0])) {
                User userByName = userReceiver.findUserByName(args[1]);
                if (userByName == null) {
                    println(StringFormatTemplate.USER_NO_EXIST_FORMAT_CONSOLE, args[1]);
                } else {
                    UserSession userSession = UserSession.getInstance();
                    userSession.setCacheUser(userByName);
                    userSession.setInPasswordStatus(true);
                    print(StringConstant.LOGIN_PASSWORD_PROMPT_CONSOLE);
                }
            } else {
                print(StringConstant.LOGIN_ERROR_PROMPT_CONSOLE);
            }
        } else {
            print(StringConstant.LOGIN_ERROR_PROMPT_CONSOLE);
        }
    }
}
