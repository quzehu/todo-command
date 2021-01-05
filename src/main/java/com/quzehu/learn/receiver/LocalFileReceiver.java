package com.quzehu.learn.receiver;

import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
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
public class LocalFileReceiver implements Receiver {


    private final AbstractMemoryReceiver receiver;

    @Value("${todo.basePath}")
    private String basePath;

    @Value("${todo.fileName}")
    private String fileName;

    private File file;

    public LocalFileReceiver(AbstractMemoryReceiver receiver) {
        this.receiver = receiver;
    }


    @Override
    public List<TodoItem> list() {
        // TODO 目前针对的是待办事项不多的情况，直接读内存缓存
        //  这里可以加一个策略，数据量多的时候，可以从其他地方读取，只是有个想法以后再说
        // 先从内存中读取，是空的再从文件中读取
        List<TodoItem> todoItems;
        todoItems = receiver.list();
        if (todoItems.isEmpty()) {
            todoItems = listAllOfFile();
            // 放入内存中
            receiver.addAll(todoItems);
        }
        return todoItems;
    }

    @Override
    public List<TodoItem> list(String... args) {
        // 先从内存中读取，是空的再从文件中读取
        List<TodoItem> todoItems;
        todoItems = receiver.list(args);
        if (todoItems.isEmpty()) {
            todoItems = listAllOfFile();
            // 放入内存中
            receiver.addAll(todoItems);
            // 过滤出已完成的
            todoItems = todoItems.stream()
                    .filter(item -> ItemStatusEnum.DONE.getStatus().equals(item.getStatus()))
                    .collect(Collectors.toList());
        }
        return todoItems;
    }

    @Override
    public TodoItem valueOf(int index) {
        return receiver.valueOf(index);
    }

    @Override
    public boolean done(int index) {
        boolean done = receiver.done(index);
        if (done) {
            String rowText = FileUtils.readFileLine(basePath, fileName, index);
            String[] arrays = rowText.split(" ");
            arrays[2] = String.valueOf(ItemStatusEnum.DONE.getStatus());
            String newRowText = getNewRowText(arrays);
            FileUtils.writeFile(getFile(), index, newRowText);
        }
        return done;
    }


    @Override
    public int add(String text) {
        int index = receiver.add(text);
        FileUtils.writeFileEnd(getFile(), getAddNewRowText(String.valueOf(index), text));
        return index;
    }

    private String getAddNewRowText(String index, String text) {
        String[] args = new String[]{index, text, ItemStatusEnum.NOT_DONE.getStatus().toString(), "admin"};
        return getNewRowText(args);
    }

    private String getNewRowText(String ...args) {
        return String.format(StringFormatTemplate.FORMAT_FILE, args);
    }

    private File getFile() {
        if (file == null) {
            file = FileUtils.createFile(basePath, fileName);
        }
        return file;
    }
    private List<TodoItem> listAllOfFile() {
        getFile();
        List<String> textList = FileUtils.readFile(basePath, fileName);
        return textList.stream().map(item -> {
            String[] arrays = item.split(" ");
            TodoItem todoItem = new TodoItem();
            if (arrays.length == 4) {
                todoItem.setIndex(Integer.valueOf(arrays[0]));
                todoItem.setText(arrays[1]);
                todoItem.setStatus(Integer.valueOf(arrays[2]));
                todoItem.setUserId(arrays[3]);
            }
            return todoItem;
        }).collect(Collectors.toList());
    }
}
