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

    private final UserReceiver userReceiver;

    static {
        List<Options> optionsList = new ArrayList<>();
        optionsList.add(new Options(StringConstant.LOGIN_COMMAND, "-u", "选择登录用户，后面接用户名"));
        optionsList.add(new Options(StringConstant.LOGIN_COMMAND, "-r", "注册用户，后面接新的用户名"));
        optionsList.add(new Options(StringConstant.LOGIN_COMMAND, "-l", "查看所有用户列表"));
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
        ifPresentOrElse(args.length == 2, args, this::moreArgsAction, this::oneArgsAction);
    }

    private void oneArgsAction(String[] args) {
        ifPresentOrElse("-l".equals(args[0]), this::listAction, this::errorActionTwo);
    }

    private void listAction() {
        List<User> users = userReceiver.findAllUsers();
        users.forEach(item -> println(item.toString()));
    }

    private void errorActionTwo() {
        println(StringConstant.LOGIN_ERROR_PROMPT_CONSOLE);
    }

    private void moreArgsAction(String[] args) {
        // 登录
        boolean loginFlag = ifPresent("-u".equals(args[0]), args[1], this::loginAction);
        //注册
        boolean registerFlag = ifPresent("-r".equals(args[0]), args[1], this::registeredAction);

        orElse(loginFlag || registerFlag, this::errorActionOne);
    }

    private void loginAction(String arg) {
        User userByName = userReceiver.findUserByName(arg);
        Optional<User> optional = Optional.ofNullable(userByName);
        optional.ifPresent((user) -> {
            // 缓存登录用户
            UserSessionUtils.cacheLoginUserToSession(user);
            print(StringConstant.LOGIN_PASSWORD_PROMPT_CONSOLE);
        });
        if (!optional.isPresent()) {
            // 用户不是系统用户
            println(StringFormatTemplate.USER_NO_EXIST_FORMAT_CONSOLE, arg);
        }
    }

    private void registeredAction(String arg) {
        User userByName = userReceiver.findUserByName(arg);
        if (userByName != null) {
            // 不允许注册，存在相同的用户
            println(StringConstant.REGISTER_ERROR_SAME_USER_PROMPT_CONSOLE);
            return;
        }
        UserSessionUtils.registeredUserOfSession(arg);
        print(StringConstant.LOGIN_PASSWORD_PROMPT_CONSOLE);
    }

    private void errorActionOne() {
        println(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE);
    }





}
