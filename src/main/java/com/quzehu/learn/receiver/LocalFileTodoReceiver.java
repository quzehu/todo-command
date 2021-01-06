package com.quzehu.learn.receiver;

import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 用本地文件作为待办事项的存储容器
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.LocalFileReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/4 9:47
 * @Version 1.0
 */
@Component
@Lazy
public class LocalFileTodoReceiver implements TodoReceiver {


    private final AbstractMemoryTodoReceiver todoReceiver;


    private final TodoConfig config;

    private File file;

    public LocalFileTodoReceiver(@Qualifier("memoryTodoReceiver") AbstractMemoryTodoReceiver todoReceiver,
                                 TodoConfig config) {
        this.todoReceiver = todoReceiver;
        this.config = config;
        cacheList();
    }


    @Override
    public List<TodoItem> list() {
        return todoReceiver.list();
    }



    @Override
    public List<TodoItem> list(String... args) {
        return todoReceiver.list(args);
    }

    @Override
    public TodoItem valueOf(int index) {
        return todoReceiver.valueOf(index);
    }

    @Override
    public boolean done(int index) {
        boolean done = todoReceiver.done(index);
        // 同步更新文件
        if (done) {
            String rowText = FileUtils.readFileLine(config.getBasePath(), config.getFileName(), index);
            String[] arrays = rowText.split(" ");
            arrays[2] = String.valueOf(ItemStatusEnum.DONE.getStatus());
            String newRowText = getNewRowText(arrays);
            FileUtils.writeFileToLine(getFile(), index, newRowText);
        }
        return done;
    }


    @Override
    public int add(String text) {
        int index = todoReceiver.add(text);
        // 同步更新文件
        FileUtils.writeFileEnd(getFile(), getAddNewRowText(String.valueOf(index), text));
        return index;
    }

    private String getAddNewRowText(String index, String text) {
        // Todo 增加用户
        String[] args = new String[]{index, text, ItemStatusEnum.NOT_DONE.getStatus().toString(), "0"};
        return getNewRowText(args);
    }

    private String getNewRowText(String ...args) {
        return String.format(StringFormatTemplate.FORMAT_FILE, args);
    }

    private File getFile() {
        if (file == null) {
            file = FileUtils.createFile(config.getBasePath(), config.getFileName());
        }
        return file;
    }

    private List<TodoItem> listAllOfFile() {
        getFile();
        List<String> textList = FileUtils.readFile(config.getBasePath(), config.getFileName());
        return todoReceiver.convertTodoList(textList);
    }

    private void cacheList() {
        List<TodoItem> todoItems;
        todoItems = todoReceiver.getItems();
        if (todoItems.isEmpty()) {
            todoItems = listAllOfFile();
            // 放入内存中
            todoReceiver.addAll(todoItems);
        }
    }
}
