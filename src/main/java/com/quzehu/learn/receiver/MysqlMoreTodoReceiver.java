package com.quzehu.learn.receiver;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.quzehu.learn.api.Print;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.mapper.TodoItemMapper;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @Resource
    private TodoItemMapper todoItemMapper;

    private final AbstractMemoryTodoReceiver todoReceiver;

    private final TodoConfig todoConfig;

    public MysqlMoreTodoReceiver(@Qualifier("memoryMoreTodoReceiver")
                                 AbstractMemoryTodoReceiver todoReceiver,
                                 TodoConfig todoConfig) {
        this.todoReceiver = todoReceiver;
        this.todoConfig = todoConfig;
        if (StringConstant.LOAD_INIT.equals(todoConfig.getInitDataSource())) {
            cacheAllToMemory();
        }
    }

    @Override
    public List<TodoItem> list() {
        if (StringConstant.LOAD_LAZY.equals(todoConfig.getInitDataSource())) {
            cacheSingleToMemory();
        }
        return todoReceiver.list();
    }

    private void cacheAllToMemory() {
        List<TodoItem> todoItems = todoItemMapper.selectList(Wrappers.<TodoItem>lambdaQuery()
                .orderByAsc(TodoItem::getUserId)
                .orderByAsc(TodoItem::getIndexNum));
        Map<Integer, List<TodoItem>> groupByUserId =
                todoItems.stream().collect(Collectors.groupingBy(TodoItem::getUserId));
        for (Map.Entry<Integer, List<TodoItem>> entry : groupByUserId.entrySet()) {
            List<TodoItem> todoListByKey = todoReceiver.getTodoListByKey(entry.getKey());
            if (todoListByKey.isEmpty()) {
                // 初始化内存
                todoReceiver.addAllToMapByKey(entry.getKey(), todoItems);
            }
        }


    }

    private void cacheSingleToMemory() {
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems = todoItemMapper.selectList(Wrappers.<TodoItem>lambdaQuery()
                .eq(TodoItem::getUserId, userId).orderByAsc(TodoItem::getIndexNum));
        List<TodoItem> todoListByKey = todoReceiver.getTodoListByKey(userId);
        if (todoListByKey.isEmpty()) {
            // 初始化内存
            todoReceiver.addAllToMapByKey(userId, todoItems);
        }
    }


    @Override
    public List<TodoItem> list(String... args) {
        // Todo 目前所有的读取操作都是直接读取的内存，如果数据量大，则不建议用内存做缓存，需要加策略
        if (StringConstant.LOAD_LAZY.equals(todoConfig.getInitDataSource())) {
            cacheSingleToMemory();
        }
        return todoReceiver.list(args);
    }


    @Override
    public TodoItem valueOf(int index) {
        return todoReceiver.valueOf(index);
    }



    @Override
    public boolean done(int index) {
        if (StringConstant.LOAD_LAZY.equals(todoConfig.getInitDataSource())) {
            cacheSingleToMemory();
        }
        // 修改内存
        boolean done = todoReceiver.done(index);
        // 修改数据库
        TodoItem todoItem = todoReceiver.valueOf(index);
        todoItemMapper.update(todoItem, Wrappers.<TodoItem>lambdaUpdate()
                .eq(TodoItem::getUserId, todoItem.getUserId())
                .eq(TodoItem::getIndexNum, index));
        return done;
    }

    @Override
    public int add(String text) {
        if (StringConstant.LOAD_LAZY.equals(todoConfig.getInitDataSource())) {
            cacheSingleToMemory();
        }
        // 添加到内存
        int index = todoReceiver.add(text);
        // 添加到数据库
        TodoItem todoItem = todoReceiver.valueOf(index);
        todoItemMapper.insert(todoItem);
        return index;
    }

    @Override
    public void exportFile(String... args) {

    }

    @Override
    public void importFile(List<TodoItem> todoItems) {

    }

    @Override
    public void clearList() {

    }
}
