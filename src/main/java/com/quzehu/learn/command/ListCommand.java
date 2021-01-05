package com.quzehu.learn.command;

import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.receiver.Receiver;

import java.util.List;

/**
 * 列表命令
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.command.ListCommand
 * @Author Qu.ZeHu
 * @Date 2021/1/3 15:38
 * @Version 1.0
 */
public class ListCommand implements Command {

    private final Receiver receiver;

    public ListCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        List<TodoItem> todoItems = receiver.list();
        todoItems.forEach(System.out::println);
        System.out.println("Total: " + todoItems.size() + " items.");
    }

    @Override
    public void execute(String... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("The args length must be one!");
        }
        List<TodoItem> todoItems = receiver.list(args);
        todoItems.forEach(System.out::println);
        long doneSize = todoItems
                .stream().filter(item -> ItemStatusEnum.DONE.getStatus().equals(item.getStatus())).count();
        System.out.println("Total: " + todoItems.size() + " items, " + doneSize + " item done.");
    }
}
