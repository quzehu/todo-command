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

    private  UserConfig config;

    private UserReceiver userReceiver;

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
        Boolean registeredStatus = UserSessionUtils.getUserSession().getRegisteredStatus();
        if (registeredStatus) {
            if (UserSessionUtils.getPasswordCountBySession() != 0) {
                String password = UserSessionUtils.getPasswordBySession();
                if (password.equals(args[0])) {
                    // 注册成功 添加用户 并登录
                    User user = userReceiver.addUser(UserSessionUtils.getUserNameBySession(), password);
                    UserSessionUtils.cacheLoginUserToSession(user);
                    UserSessionUtils.login();
                    println(StringConstant.REGISTER_SUCCESS_PROMPT_CONSOLE);
                } else {
                    // 注册失败 清空
                    println(StringConstant.REGISTER_ERROR_PASSWORD_MATCH_PROMPT_CONSOLE);
                    UserSessionUtils.clearLoginUserOfSession();
                }
            } else {
                UserSessionUtils.cachePasswordOfSession(args[0]);
                print(StringConstant.PASSWORD_AGAIN_PROMPT_CONSOLE);
            }
        } else {
            // 得到缓存用户
            Optional<User> userOptional = Optional.ofNullable(UserSessionUtils.getUserBySession());
            userOptional.ifPresent(user -> {
                String v2 = UserSessionUtils.getPasswordBySession();
                ifPresentOrElse(args[0], v2, loginAction, errorAction);
            });
            if (!userOptional.isPresent()) {
                println(StringConstant.PASSWORD_ERROR_PROMPT_CONSOLE);
            }
        }
    }
    /**
     * 退出执行的动作
     * @Date 2021/1/7 21:44
     * @Author Qu.ZeHu
     * @return
     **/
    private final Runnable exitAction = () -> {
        // 退出
        println(StringConstant.PASSWORD_ERROR_EXIT_CONSOLE);
        UserSessionUtils.exit();
        // 密码计数器加一
        UserSessionUtils.passwordCountAddOne();
        System.exit(0);
    };
    /**
     * 再来一次的执行动作
     * @Date 2021/1/7 21:44
     * @Author Qu.ZeHu
     * @return
     **/
    private final Runnable againAction = () -> {
        println(StringConstant.PASSWORD_ERROR_AGAIN_CONSOLE);
        // 密码计数器加一
        UserSessionUtils.passwordCountAddOne();
    };
    /**
     * 登录的的执行动作
     * @Date 2021/1/7 21:44
     * @Author Qu.ZeHu
     * @return
     **/
    private final Runnable loginAction = () -> {
        // 登录
        UserSessionUtils.login();
        println(StringConstant.PASSWORD_SUCCESS_CONSOLE);
    };
    /**
     * 错误的执行动作
     * @Date 2021/1/7 21:44
     * @Author Qu.ZeHu
     * @return
     **/
    private final Runnable errorAction = () -> {
        Integer passwordCount = UserSessionUtils.getPasswordCountBySession();
        ifPresentOrElse(config.getPwCheckNum(), passwordCount, exitAction, againAction);
    };

}
