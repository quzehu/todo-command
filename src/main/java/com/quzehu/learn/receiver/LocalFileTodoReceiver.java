package com.quzehu.learn.receiver;

import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class LocalFileTodoReceiver implements TodoReceiver {


    private final AbstractMemoryTodoReceiver receiver;


    private final TodoConfig config;

    private File file;

    public LocalFileTodoReceiver(@Qualifier("memoryTodoReceiver") AbstractMemoryTodoReceiver receiver,
                                 TodoConfig config) {
        this.receiver = receiver;
        this.config = config;
        cacheList();
    }


    @Override
    public List<TodoItem> list() {
       // cacheList();
        return receiver.list();
    }



    @Override
    public List<TodoItem> list(String... args) {
       // cacheList();
        return receiver.list(args);
    }

    @Override
    public TodoItem valueOf(int index) {
        return receiver.valueOf(index);
    }

    @Override
    public boolean done(int index) {
       // cacheList();
        boolean done = receiver.done(index);
        // 同步更新文件
        if (done) {
            String rowText = FileUtils.readFileLine(config.getBasePath(), config.getFileName(), index);
            String[] arrays = rowText.split(" ");
            arrays[2] = String.valueOf(ItemStatusEnum.DONE.getStatus());
            String newRowText = getNewRowText(arrays);
            FileUtils.writeFileLine(getFile(), index, newRowText);
        }
        return done;
    }


    @Override
    public int add(String text) {
       // cacheList();
        int index = receiver.add(text);
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
        return textList.stream().map(item -> {
            String[] arrays = item.split(" ");
            TodoItem todoItem = new TodoItem();
            if (arrays.length == 4) {
                todoItem.setIndex(Integer.valueOf(arrays[0]));
                todoItem.setText(arrays[1]);
                todoItem.setStatus(Integer.valueOf(arrays[2]));
                todoItem.setUserId(Integer.valueOf(arrays[3]));
            }
            return todoItem;
        }).collect(Collectors.toList());
    }

    private void cacheList() {
        List<TodoItem> todoItems;
        todoItems = receiver.getItems();
        if (todoItems.isEmpty()) {
            todoItems = listAllOfFile();
            // 放入内存中
            receiver.addAll(todoItems);
        }
    }
}
