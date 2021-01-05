package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.LoginStatus;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.config.UserConfig;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserStatus;
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
public class PasswordCommand implements Command, Print, LoginStatus {

    @Autowired
    private  UserConfig config;

    @Override
    public void execute() {
        println("Please input a password!");
    }

    @Override
    public void execute(String ...args) throws IllegalArgumentException {
        UserStatus userStatus = getLoginStatus();
        User cacheUser = userStatus.getCacheUser();
        Integer passwordNum = userStatus.getPasswordNum();
        if (cacheUser != null) {
            String password = cacheUser.getPassword();
            // Todo md5加密
            if (password.equals(args[0])) {
                println("Login success!");
                // 清除缓存用户
                userStatus.setCacheUser(null);
            } else {
                if (config.getPwCheckNum().equals(passwordNum)) {
                    println("Password error, exit!");
                    // 清空缓存数据
                    userStatus.setCacheUser(null);
                    userStatus.setPasswordNum(0);
                    userStatus.setStatus(false);
                } else {
                    println("Password error, Please input again!");
                }
                userStatus.setPasswordNum(++passwordNum);
            }
        } else {
         println("Please input 'todo login -u user' in first, user must be a system user!");
        }
    }
}
