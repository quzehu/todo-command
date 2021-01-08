package com.quzehu.learn.invoker;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.command.CommandFactory;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;
import com.quzehu.learn.utils.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
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
public class Invoker implements Print, IfOrElse {

    /**
     * 调用命令
     * @Date 2021/1/8 0:14
     * @param inputCommand 用户输入的命令
     * @Author Qu.ZeHu
     * @return void
     **/
    public void callTodo(String inputCommand) {
        // 如果不是todo开头
        if (!inputCommand.startsWith(StringConstant.TODO_COMMAND)) {
            println(StringConstant.TODO_ERROR_PROMPT_CONSOLE);
            return;
        }

        String[] arrays = inputCommand.split(" ");
        if (arrays.length == 1) {
            println(StringConstant.TODO_ERROR_NO_PARAM_PROMPT_CONSOLE);
            return;
        }

        try {
            Command command = CommandFactory.getInstance().createCommand(arrays[1]);
            if (arrays.length > 2) {
                String[] newArrays = new String[arrays.length - 2];
                System.arraycopy(arrays, 2, newArrays, 0, newArrays.length);
                // 仅传递todo login/add/done/list/等 后面的参数
                command.execute(newArrays);
            } else {
                command.execute();
            }
        }catch (IllegalArgumentException e) {
            println(e.getMessage());
        }

    }

    /**
     * 调用密码命令
     * @Date 2021/1/8 0:14
     * @param password 密码
     * @Author Qu.ZeHu
     * @return void
     **/
    public void callPassword(String password) {
        try {
            Command command = CommandFactory.getInstance()
                    .createCommand(StringConstant.PASSWORD_COMMAND);
            command.execute(password);
        }catch (IllegalArgumentException e) {
            println(e.getMessage());
        }
    }

    /**
     * 循环调用命令
     * @Date 2021/1/8 0:13
     * @Author Qu.ZeHu
     * @return void
     **/
    public void callLoop() {
        println(StringConstant.FIRST_CONSTANT_CONSOLE);

        Scanner scanner = new Scanner(System.in);
        print(StringConstant.PREFIX_CONSTANT_CONSOLE);
        UserSession userSession = UserSessionUtils.getUserSession();

        while (userSession.getNormalStatus() && scanner.hasNext()) {
            String nextLine = scanner.nextLine().trim().toLowerCase();
            // 退出命令
            ifPresent(StringConstant.EXIT_COMMAND.equals(nextLine),
                    () -> { println(StringConstant.EXIT_SUCCESS_CONSOLE); System.exit(0);});

            // 判断是否是密码输入，调用不同的命令
            ifPresentOrElse(UserSessionUtils.getUserSession()
                            .getInPasswordStatus(), () -> callPassword(nextLine), () -> callTodo(nextLine));
            // 得到登录用户
            User cacheUser = UserSessionUtils.getUserBySession();
            Optional<User> optional = Optional.ofNullable(cacheUser);

            // 区分是否登录的状态
            // 登录
            optional.ifPresent(user -> ifPresentOrElse(UserSessionUtils.getUserSession().getInPasswordStatus(),
                    printPrefixAction, printPrefixWithUserAction));

            // 没登录
            if (!optional.isPresent()) {
                print(StringConstant.PREFIX_CONSTANT_CONSOLE);
            }

        }

    }

    /**
     * 前缀打印动作
     * @Date 2021/1/8 0:11
     * @Author Qu.ZeHu
     * @return
     **/
    private final Runnable printPrefixAction = () -> print(StringConstant.PREFIX_CONSTANT_CONSOLE);

    /**
     * 带用户前缀的打印动作
     * @Date 2021/1/8 0:11
     * @Author Qu.ZeHu
     * @return
     **/
    private final Runnable printPrefixWithUserAction =
            () -> print(StringFormatTemplate.PREFIX_FORMAT_CONSOLE,
                        UserSessionUtils.getUserBySession().getUserName());

}
