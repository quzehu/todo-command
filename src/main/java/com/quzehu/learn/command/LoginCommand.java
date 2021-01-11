package com.quzehu.learn.command;

import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.Options;
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
public class LoginCommand extends AbstractCommand implements IfOrElse {

    private UserReceiver userReceiver;

    static {
        List<Options> optionsList = new ArrayList<>();
        optionsList.add(new Options(StringConstant.LOGIN_COMMAND, "-u", "选择登录用户，后面接用户名"));
        optionsList.add(new Options(StringConstant.LOGIN_COMMAND, "-h", "获取该命令的帮助"));
        getOptionsMap().put(StringConstant.LOGIN_COMMAND, optionsList);
    }

    public LoginCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }



    @Override
    public void execute() {
        println(StringConstant.LOGIN_ERROR_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String... args) throws IllegalArgumentException {
        ifPresentOrElse(args.length, 1, args[0], this::helpAction,
        () -> ifPresentOrElse(args.length, 2, args, normalAction, errorAction));
    }

    private void helpAction(String v) {
        ifPresentOrElse("-h", v, StringConstant.LOGIN_COMMAND, this::printAllOptionsAction, this::exceptionAction);
    }

    private void exceptionAction() {
        throw new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE);
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
