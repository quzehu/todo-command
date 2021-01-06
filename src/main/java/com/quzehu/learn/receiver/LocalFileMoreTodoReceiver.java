package com.quzehu.learn.receiver;

import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.model.User;
import com.quzehu.learn.utils.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 多用户本地文件存储接收者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.LocalFileMoreTodoReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/6 16:45
 * @Version 1.0
 */
@Component
public class LocalFileMoreTodoReceiver  implements TodoReceiver {

    private final AbstractMemoryTodoReceiver todoReceiver;

    private final UserReceiver userReceiver;

    private final TodoConfig config;
    private File file;
    private Map<Integer, File> fileMap;

    public LocalFileMoreTodoReceiver(@Qualifier("memoryMoreTodoReceiver")
                                             AbstractMemoryTodoReceiver todoReceiver,
                                     UserReceiver userReceiver,
                                     TodoConfig config) {
        this.todoReceiver = todoReceiver;
        this.userReceiver = userReceiver;
        this.config = config;

        if (StringConstant.LOAD_INIT.equals(config.getInitFile())) {
            cacheAllList();
        }
    }


    @Override
    public List<TodoItem> list() {
        // cacheList();
        return todoReceiver.list();
    }



    @Override
    public List<TodoItem> list(String... args) {
        // cacheList();
        return todoReceiver.list(args);
    }

    @Override
    public TodoItem valueOf(int index) {
        return todoReceiver.valueOf(index);
    }

    @Override
    public boolean done(int index) {
        // cacheList();
        boolean done = todoReceiver.done(index);
        // 同步更新文件
        if (done) {
            String rowText = FileUtils.readFileLine(config.getBasePath(), config.getFileName(), index);
            String[] arrays = rowText.split(" ");
            arrays[2] = String.valueOf(ItemStatusEnum.DONE.getStatus());
            String newRowText = getNewRowText(arrays);
            FileUtils.writeFileLine(createFile(), index, newRowText);
        }
        return done;
    }


    @Override
    public int add(String text) {
        // cacheList();
        int index = todoReceiver.add(text);
        // 同步更新文件
        FileUtils.writeFileEnd(createFile(), getAddNewRowText(String.valueOf(index), text));
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

    private File createFile() {
        if (file == null) {
            file = FileUtils.createFile(config.getBasePath(), config.getFileName());
        }
        return file;
    }

    private Map<Integer, File> createFileMap() {
        List<User> allUsers = userReceiver.findAllUsers();

        allUsers.forEach(user -> {
            fileMap.put(user.getId(), FileUtils.createFile(config.getBasePath(),
                    user.getUserName() + config.getFileName()));
        });
        return null;
    }


    private List<TodoItem> listAllOfFile() {
        createFile();
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

    }

    private void cacheAllList() {
        // 读用户配置文件
        List<User> allUsers = userReceiver.findAllUsers();
        allUsers.forEach(user -> {
            List<TodoItem> todoListByKey = todoReceiver.getTodoListByKey(user.getId());

        });

        List<TodoItem> todoItems;
        todoItems = todoReceiver.getItems();
        if (todoItems.isEmpty()) {
            todoItems = listAllOfFile();
            // 放入内存中
            todoReceiver.addAll(todoItems);
        }
    }
}
