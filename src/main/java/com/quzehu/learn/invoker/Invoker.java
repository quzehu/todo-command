package com.quzehu.learn.invoker;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.command.CommandFactory;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Scanner;

/**
 * 命令调用者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.invoker.Invoker
 * @Author Qu.ZeHu
 * @Date 2021/1/3 18:44
 * @Version 1.0
 */
@Component
@Slf4j
public class Invoker implements Print {

    public void call(String commandStr) {
        boolean startsWith = commandStr.startsWith("todo");
        // 如果不是todo开头
        if (!startsWith) {
            println("command is wrong.");
            return;
        }
        String[] arrays = commandStr.split(" ");
        if (arrays.length == 1) {
            println("command is wrong.");
            return;
        }
        try {
            Command command = CommandFactory.getInstance().createCommand(arrays[1]);
            if (arrays.length == 2) {
                command.execute();
            } else {
                String[] newArray = new String[arrays.length - 2];
                System.arraycopy(arrays, 2, newArray, 0, newArray.length);
                command.execute(newArray);
            }
        }catch (IllegalArgumentException e) {
            println(e.getMessage());
        }
    }

    public void callPassword(String password) {
        try {
            Command command = CommandFactory.getInstance().createCommand("password");
            command.execute(password);
        }catch (IllegalArgumentException e) {
            println(e.getMessage());
        }
    }

    public void callLoop() {
        println("Please input command:");
        Scanner scanner = new Scanner(System.in);
        print(StringConstant.PREFIX_CONSTANT_CONSOLE);
        UserSession userSession = UserSession.getInstance();
        while (scanner.hasNext() && userSession.getNormalStatus()) {
            String nextLine = scanner.nextLine().trim().toLowerCase();

            if ("exit".startsWith(nextLine)) {
                println("exit success!");
                break;
            }
            if (userSession.getInPasswordStatus()) {
                callPassword(nextLine);
            }else {
                call(nextLine);
            }
            User cacheUser = userSession.getCacheUser();
            if (cacheUser == null) {
             print(StringConstant.PREFIX_CONSTANT_CONSOLE);
            } else {
                if (userSession.getInPasswordStatus()) {
                    print(StringConstant.PREFIX_CONSTANT_CONSOLE);
                } else {
                    print(StringFormatTemplate.PREFIX_FORMAT_CONSOLE, cacheUser.getUserName());
                }
            }

        }

    }
}
