package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.Parameter;
import com.quzehu.learn.model.User;
import com.quzehu.learn.utils.UserSessionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 登录命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.LoginCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/5 21:11
 * @Version 1.0
 */
public class LoginCommand implements Command, Print, IfOrElse {

    private UserReceiver userReceiver;

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
        ifPresentOrElse(args.length, 2, args, normalAction, errorAction);
    }

    /**
     * 登录的执行动作
     * @Date 2021/1/7 21:48
     * @Author Qu.ZeHu
     * @return
     **/
    private final Consumer<String> loginAction = (v) -> {
        User userByName = userReceiver.findUserByName(v);
        Optional<User> optional = Optional.ofNullable(userByName);
        optional.ifPresent((user) -> {
            // 缓存登录用户
            UserSessionUtils.cacheLoginUserToSession(user);
            print(StringConstant.LOGIN_PASSWORD_PROMPT_CONSOLE);
        });
        if (!optional.isPresent()) {
            // 用户不是系统用户
            println(StringFormatTemplate.USER_NO_EXIST_FORMAT_CONSOLE, v);
        }
    };
    /**
     * 错误的执行动作
     * @Date 2021/1/7 21:48
     * @Author Qu.ZeHu
     * @return
     **/
    private final Runnable errorAction = () -> println(StringConstant.LOGIN_ERROR_PROMPT_CONSOLE);

    /**
     * 正常的执行动作
     * @Date 2021/1/7 21:48
     * @Author Qu.ZeHu
     * @return
     **/
    private final Consumer<String[]> normalAction
            = (String... v) -> ifPresentOrElse("-u", v[0], v[1], loginAction, errorAction);

}
