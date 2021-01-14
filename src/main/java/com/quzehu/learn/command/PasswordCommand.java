package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.config.UserConfig;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.User;
import com.quzehu.learn.utils.UserSessionUtils;
import java.util.Optional;

/**
 * 密码命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.PasswordCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/5 22:01
 * @Version 1.0
 */
public class PasswordCommand implements Command, Print, IfOrElse {

    private final UserConfig config;

    private final UserReceiver userReceiver;

    public PasswordCommand(UserConfig config, UserReceiver userReceiver) {
        this.config = config;
        this.userReceiver = userReceiver;
    }

    @Override
    public void execute() {
        println(StringConstant.PASSWORD_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String ...args) throws IllegalArgumentException {
        Boolean registerStatus = UserSessionUtils.getUserSession().getRegisterStatus();
        ifPresentOrElse(registerStatus, args[0], this::register, this::login);
    }

    private void register(String arg){
        Integer psCount = UserSessionUtils.getPasswordCountBySession();
        ifPresentOrElse(psCount.equals(0), arg, this::cachePassword, this::checkPassword);
    }

    private void checkPassword(String arg) {
        String password = UserSessionUtils.getPasswordBySession();
        ifPresentOrElse(password.equals(arg), password, this::registerSuccess, this::registerFail);
    }

    private void registerSuccess(String password) {
        // 注册成功 添加用户 并登录
        User user = userReceiver.addUser(UserSessionUtils.getUserNameBySession(), password);
        UserSessionUtils.cacheLoginUserToSession(user);
        UserSessionUtils.login();
        println(StringConstant.REGISTER_SUCCESS_PROMPT_CONSOLE);
    }

    private void registerFail() {
        // 注册失败 清空
        println(StringConstant.REGISTER_ERROR_PASSWORD_MATCH_PROMPT_CONSOLE);
        UserSessionUtils.clearLoginUserOfSession();
    }

    private void cachePassword(String arg) {
        // 缓存第一次输入的密码
        UserSessionUtils.cachePasswordOfSession(arg);
        print(StringConstant.PASSWORD_AGAIN_PROMPT_CONSOLE);
    }

    private void login(String arg) {
        // 得到缓存用户
        Optional<User> userOptional = Optional.ofNullable(UserSessionUtils.getUserBySession());
        userOptional.ifPresent(user -> {
            String password = UserSessionUtils.getPasswordBySession();
            ifPresentOrElse(arg.equals(password), this::loginAction, this::checkPsCountAction);
        });
        if (!userOptional.isPresent()) {
            println(StringConstant.PASSWORD_ERROR_PROMPT_CONSOLE);
        }
    }

    private void loginAction() {
        // 登录
        UserSessionUtils.login();
        println(StringConstant.PASSWORD_SUCCESS_CONSOLE);
    }

    private void checkPsCountAction() {
        Integer passwordCount = UserSessionUtils.getPasswordCountBySession();
        ifPresentOrElse(config.getPwCheckNum().equals(passwordCount), this::exitAction, this::againAction);
    }

    private void exitAction() {
        // 退出
        println(StringConstant.PASSWORD_ERROR_EXIT_CONSOLE);
        UserSessionUtils.exit();
        // 密码计数器加一
        UserSessionUtils.passwordCountAddOne();
        System.exit(0);
    }

    private void againAction() {
        println(StringConstant.PASSWORD_ERROR_AGAIN_CONSOLE);
        // 密码计数器加一
        UserSessionUtils.passwordCountAddOne();
    }

}
