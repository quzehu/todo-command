package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.config.UserConfig;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 密码命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.PasswordCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/5 22:01
 * @Version 1.0
 */

@Component
public class PasswordCommand implements Command, Print {

    @Autowired
    private  UserConfig config;

    @Override
    public void execute() {
        println(StringConstant.PASSWORD_PROMPT_CONSOLE);
    }

    @Override
    public void execute(String ...args) throws IllegalArgumentException {
        // Todo 需要重构
        UserSession userSession = UserSession.getInstance();
        User cacheUser = userSession.getCacheUser();
        Integer passwordCount = userSession.getInPasswordCount();
        if (cacheUser != null) {
            String password = cacheUser.getPassword();
            // Todo md5加密
            if (password.equals(args[0])) {
                println(StringConstant.PASSWORD_SUCCESS_CONSOLE);
                // 设置为已经登录状态
                userSession.setLoginStatus(true);
                // 清空是密码登录的状态
                userSession.setInPasswordStatus(false);
            } else {
                if (config.getPwCheckNum().equals(passwordCount)) {
                    println(StringConstant.PASSWORD_ERROR_EXIT_CONSOLE);
                    // 清空缓存数据
                    userSession.setCacheUser(null);
                    userSession.setInPasswordCount(0);
                    userSession.setNormalStatus(false);
                } else {
                    println(StringConstant.PASSWORD_ERROR_AGAIN_CONSOLE);
                }
                userSession.setInPasswordCount(++passwordCount);
            }
        } else {
            println(StringConstant.PASSWORD_ERROR_PROMPT_CONSOLE);
        }
    }
}
