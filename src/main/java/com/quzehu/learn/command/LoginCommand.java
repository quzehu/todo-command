package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.LoginStatus;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserStatus;
import com.quzehu.learn.api.UserReceiver;

/**
 * 登录命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.LoginCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/5 21:11
 * @Version 1.0
 */
public class LoginCommand implements Command, Print, LoginStatus {

    private final UserReceiver userReceiver;

    public LoginCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }


    @Override
    public void execute() {
        print("Please input 'todo login -u user' user must be a system user!");
    }

    @Override
    public void execute(String... args) throws IllegalArgumentException {
        if (args.length == 2) {
            if ("-u".equals(args[0])) {
                User userByName = userReceiver.findUserByName(args[1]);
                if (userByName == null) {
                    println(StringFormatTemplate.USER_NO_EXIST_FORMAT_CONSOLE, args[1]);
                } else {
                    UserStatus userStatus = getLoginStatus();
                    userStatus.setCacheUser(userByName);
                    userStatus.setInputPsCommand(true);
                    print("Password:");
                }
            } else {
                print("Please input 'todo login -u user' user must be a system user!");
            }
        } else {
            print("Please input 'todo login -u user' user must be a system user!");
        }
    }
}
