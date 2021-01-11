package com.quzehu.learn.receiver;

import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.model.TodoItem;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Mysql数据库接收者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.MysqlMoreTodoReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/10 11:41
 * @Version 1.0
 */
@Component
@Lazy
public class MysqlMoreTodoReceiver implements TodoReceiver, Print {

    @Override
    public List<TodoItem> list() {
        return null;
    }

    @Override
    public List<TodoItem> list(String... args) {
        return null;
    }

    @Override
    public TodoItem valueOf(int index) {
        return null;
    }

    @Override
    public boolean done(int index) {
        return false;
    }

    @Override
    public int add(String text) {
        return 0;
    }

    @Override
    public void exportFile(String... args) {

    }

    @Override
    public void importFile(String... args) {

    }
}
