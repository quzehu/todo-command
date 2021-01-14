package com.quzehu.learn.invoker;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.command.CommandFactory;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;
import com.quzehu.learn.utils.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
public class Invoker implements Print, IfOrElse {

    private final CommandFactory commandFactory = CommandFactory.getInstance();

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

        while (scanner.hasNext()) {
            String nextLine = scanner.nextLine().trim().toLowerCase();
            // 判断退出命令
            ifPresent(StringConstant.EXIT_COMMAND.equals(nextLine), this::exitAction);

            // 判断是否是密码输入，调用不同的命令
            Boolean psStatus = UserSessionUtils.getUserSession().getInPasswordStatus();
            ifPresentOrElse(psStatus, nextLine, this::callPassword, this::callTodo);

            // 得到登录用户
            User cacheUser = UserSessionUtils.getUserBySession();
            // 区分是否登录的状态
            ifPresentOrElse(cacheUser != null, this::loginPrint, this::noLoginPrint);
        }
    }

    public void callTodo(String inputCommand) {
        // 如果不是todo开头
        if (!inputCommand.startsWith(StringConstant.TODO_COMMAND)) {
            println(StringConstant.TODO_ERROR_PROMPT_CONSOLE);
            return;
        }

        String[] arrays = inputCommand.split("\\s+");
        if (arrays.length == 1) {
            println(StringConstant.TODO_ERROR_NO_PARAM_PROMPT_CONSOLE);
            return;
        }

        try {
            Command command = commandFactory.createCommand(arrays[1]);
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

    public void callPassword(String password) {
        try {
            Command command = commandFactory.createCommand(StringConstant.PASSWORD_COMMAND);
            command.execute(password);
        }catch (IllegalArgumentException e) {
            println(e.getMessage());
        }
    }

    private void loginPrint() {
        Boolean psStatus = UserSessionUtils.getUserSession().getInPasswordStatus();
        ifPresentOrElse(psStatus, this::printPrefixAction, this::printPrefixWithUserAction);
    }

    private void noLoginPrint() {
        print(StringConstant.PREFIX_CONSTANT_CONSOLE);
    }

    private void exitAction() {
        println(StringConstant.EXIT_SUCCESS_CONSOLE);
        System.exit(0);
    }

    private void printPrefixAction(){
        print(StringConstant.PREFIX_CONSTANT_CONSOLE);
    }

    private void printPrefixWithUserAction(){
        print(StringFormatTemplate.PREFIX_FORMAT_CONSOLE, UserSessionUtils.getUserBySession().getUserName());
    }

}
