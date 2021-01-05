package com.quzehu.learn.invoker;

import com.quzehu.learn.command.Command;
import com.quzehu.learn.command.CommandFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
public class Invoker {

    public void call(String commandStr) {
        boolean startsWith = commandStr.startsWith("todo");
        // 如果不是todo开头
        if (!startsWith) {
            System.out.println("command is wrong.");
            return;
        }
        String[] arrays = commandStr.split(" ");
        if (arrays.length == 1) {
            System.out.println("command is wrong.");
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
            System.out.println(e.getMessage());
        }
    }

    public void callLoop() {
        System.out.println("Please input command:");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String nextLine = scanner.nextLine().trim().toLowerCase();
            if ("exit".startsWith(nextLine)) {
                System.out.println("exit success!");
                break;
            }
            call(nextLine);
        }

    }
}
