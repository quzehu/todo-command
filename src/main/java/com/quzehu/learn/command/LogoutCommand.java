package com.quzehu.learn.command;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;

/**
 * 退出命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.LogoutCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/6 14:09
 * @Version 1.0
 */
public class LogoutCommand implements Command, Print {

    @Override
    public void execute(String... args) throws IllegalArgumentException {
        println(StringConstant.LOGOUT_ERROR_PROMPT_CONSOLE);
    }

    @Override
    public void execute() {
        UserSession userSession = UserSession.getInstance();
        // 清空数据
        userSession.setCacheUser(null);
        userSession.setInPasswordCount(0);
        userSession.setLoginStatus(false);
        println(StringConstant.LOGOUT_SUCCESS_FORMAT_CONSOLE);
    }
}
