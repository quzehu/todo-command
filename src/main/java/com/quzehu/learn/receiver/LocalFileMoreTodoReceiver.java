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
import com.quzehu.learn.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Lazy
public class LocalFileMoreTodoReceiver  implements TodoReceiver {

    private final AbstractMemoryTodoReceiver todoReceiver;

    private final UserReceiver userReceiver;

    private final TodoConfig config;

    private final Map<Integer, File> fileMap;

    public LocalFileMoreTodoReceiver(@Qualifier("memoryMoreTodoReceiver")
                                             AbstractMemoryTodoReceiver todoReceiver,
                                     UserReceiver userReceiver,
                                     TodoConfig config) {
        this.todoReceiver = todoReceiver;
        this.userReceiver = userReceiver;
        this.config = config;
        this.fileMap = new HashMap<>();
        if (StringConstant.LOAD_INIT.equals(config.getInitFile())) {
            cacheAllFile();
        }
    }


    @Override
    public List<TodoItem> list() {
        if (StringConstant.LOAD_LAZY.equals(config.getInitFile())) {
            cacheSingleFile();
        }
        return todoReceiver.list();
    }



    @Override
    public List<TodoItem> list(String ...args) {
        if (StringConstant.LOAD_LAZY.equals(config.getInitFile())) {
            cacheSingleFile();
        }
        return todoReceiver.list(args);
    }

    @Override
    public TodoItem valueOf(int index) {
        return todoReceiver.valueOf(index);
    }

    @Override
    public boolean done(int index) {
        if (StringConstant.LOAD_LAZY.equals(config.getInitFile())) {
            cacheSingleFile();
        }
        boolean done = todoReceiver.done(index);
        // 同步更新文件
        if (done) {
            String rowText = readRowTextFromFile(index);
            String[] arrays = rowText.split("\\s+");
            arrays[2] = String.valueOf(ItemStatusEnum.DONE.getStatus());
            String newRowText = getNewRowText(arrays);
            FileUtils.writeFileToLine(fileMap.get(UserSessionUtils.getUserIdBySession()), index, newRowText);
        }
        return done;
    }



    @Override
    public int add(String text) {
        if (StringConstant.LOAD_LAZY.equals(config.getInitFile())) {
            cacheSingleFile();
        }
        int index = todoReceiver.add(text);
        // 同步更新文件
        FileUtils.writeFileEndAppend(fileMap.get(UserSessionUtils.getUserIdBySession()),
                getAddNewRowText(String.valueOf(index), text));
        return index;
    }

    /**
     * 从文件中读取一行数据，根据索引
     * @Date 2021/1/12 17:28
     * @param index 索引
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    private String readRowTextFromFile(int index) {
        String userName = UserSessionUtils.getUserNameBySession();
        return FileUtils.readFileFromLine(config.getBasePath(),
                String.format(StringFormatTemplate.USER_FILE_NAME_FORMAT, userName, config.getFileName()), index);
    }

    /**
     * 得到一行添加美容
     * @Date 2021/1/12 17:29
     * @param index 索引
     * @param text 文本
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    private String getAddNewRowText(String index, String text) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        String[] args = new String[]{index, text, ItemStatusEnum.NOT_DONE.getStatus().toString(), String.valueOf(userId)};
        return getNewRowText(args);
    }

    private String getNewRowText(String ...args) {
        return String.format(StringFormatTemplate.FORMAT_FILE, args);
    }

    /**
     * 从文件中得到待办事项列表
     * @Date 2021/1/12 17:30
     * @param fileName 文件名
     * @Author Qu.ZeHu
     * @return java.util.List<com.quzehu.learn.model.TodoItem>
     **/
    private List<TodoItem> listAllFromFile(String fileName) {
        List<String> textList = FileUtils.readFile(config.getBasePath(), fileName);
        return todoReceiver.convertTodoList(textList);
    }


    /**
     * 缓存单个文件内容
     * @Date 2021/1/12 17:31
     * @Author Qu.ZeHu
     * @return void
     **/
    private void cacheSingleFile() {
        User userByFile = userReceiver.findUserByName(UserSessionUtils.getUserNameBySession());
        String fileName = putFileToMap(userByFile);
        List<TodoItem> todoListByKey = todoReceiver.getTodoListByKey(userByFile.getId());
        if (todoListByKey.isEmpty()) {
            // 初始化内存
            todoReceiver.addAllToMapByKey(userByFile.getId(), listAllFromFile(fileName));
        }
    }


    /**
     * 缓存所有文件内容
     * @Date 2021/1/12 17:31
     * @Author Qu.ZeHu
     * @return void
     **/
    private void cacheAllFile() {
        // 读用户配置文件
        List<User> allUsers = userReceiver.findAllUsers();
        allUsers.forEach(user -> {
            String fileName = putFileToMap(user);
            // 初始化内存
            todoReceiver.addAllToMapByKey(user.getId(), listAllFromFile(fileName));

        });
    }

    /**
     * 根据用户，把文件缓存到map中
     * @Date 2021/1/12 17:31
     * @param user 用户
     * @Author Qu.ZeHu
     * @return void
     **/
    private String putFileToMap(User user) {
        String fileName = String.format(StringFormatTemplate.USER_FILE_NAME_FORMAT,
                user.getUserName(), config.getFileName());
        // 创建文件 并且向 map中put
        if (fileMap.get(user.getId()) == null) {
            fileMap.put(user.getId(), FileUtils.createFile(config.getBasePath(), fileName));
        }
        return fileName;
    }

    @Override
    public void exportFile(String... args) {
        if (StringConstant.LOAD_LAZY.equals(config.getInitFile())) {
            cacheSingleFile();
        }
        todoReceiver.exportFile(args);
    }


    @Override
    public void importFile(List<TodoItem> todoItems) {
        // 导入到内存中
        todoReceiver.importFile(todoItems);
        // 导入到文件中
        User user = UserSessionUtils.getUserBySession();
        putFileToMap(user);
        FileUtils.writeFile(fileMap.get(user.getId()), getImportContent(todoItems), false);
    }

    /**
     * 得到导入内容，根据待办事项
     * @Date 2021/1/12 17:33
     * @param todoItems 待办事项列表
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    private String getImportContent(List<TodoItem> todoItems) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TodoItem todoItem : todoItems) {
            String[] rowArray = new String[]{String.valueOf(todoItem.getIndexNum()),
                    todoItem.getText(), String.valueOf(todoItem.getStatus()),
                    String.valueOf(todoItem.getUserId())};
            String row = String.format(StringFormatTemplate.FORMAT_FILE, rowArray);
            stringBuilder.append(row).append("\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public void clearList() {
        // 清空内存
        todoReceiver.clearList();
        // 清空文件
        User user = UserSessionUtils.getUserBySession();
        putFileToMap(user);
        FileUtils.writeFile(fileMap.get(user.getId()), "", false);
    }
}
