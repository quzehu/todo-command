package com.quzehu.learn.receiver;

import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.model.User;
import com.quzehu.learn.utils.DateUtils;
import com.quzehu.learn.utils.FileUtils;
import com.quzehu.learn.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
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
            String newRowText = getUpdateNewRowText(index);
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
                getAddNewRowText(index));
        return index;
    }

    private String readRowTextFromFile(int index) {
        String userName = UserSessionUtils.getUserNameBySession();
        return FileUtils.readFileFromLine(config.getBasePath(),
                String.format(StringFormatTemplate.USER_FILE_NAME_FORMAT, userName, config.getFileName()), index);
    }

    private String getUpdateNewRowText(Integer index) {
        TodoItem todoItem = todoReceiver.getTodoItemByIndex(index);
        Date updateTime = todoItem.getUpdateTime();
        String rowText = readRowTextFromFile(index);
        String[] arrays = rowText.split("\\s+");
        arrays[2] = String.valueOf(ItemStatusEnum.DONE.getStatus());
        if (arrays.length == 6) {
            arrays[5] = DateUtils.getDate2LongString14(updateTime);
            return getNewRowText(arrays);
        } else {
            String[] newArrays = new String[6];
            System.arraycopy(arrays, 0, newArrays, 0, arrays.length);
            newArrays[4] = DateUtils.getDate2LongString14(DateUtils.getCurrentDate());
            newArrays[5] = DateUtils.getDate2LongString14(updateTime);
            return getNewRowText(newArrays);
        }
    }

    /**
     * 获取添加到文件的一行文本，带时间
     * @Date 2021/1/11 10:43
     * @param index 参数1
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    private String getAddNewRowText(Integer index) {
        TodoItem todoItem = todoReceiver.getTodoItemByIndex(index);
        Date createTime = todoItem.getCreateTime();
        Date updateTime = todoItem.getUpdateTime();
        String[] args = new String[] { String.valueOf(index), todoItem.getText(),
                ItemStatusEnum.NOT_DONE.getStatus().toString(),
                String.valueOf(todoItem.getUserId()),
                createTime != null ? DateUtils.getDate2LongString14(createTime) : "0",
                updateTime != null ? DateUtils.getDate2LongString14(updateTime) : "0" };
        return getNewRowText(args);
    }

    /**
     * 获取添加到文件的一行文本
     * @Date 2021/1/11 10:44
     * @param index 参数1
     * @param text 参数2
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    private String getAddNewRowText(String index, String text) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        String[] args = new String[]{ index, text,
                ItemStatusEnum.NOT_DONE.getStatus().toString(),
                String.valueOf(userId) };
        return getNewRowText(args);
    }

    private String getNewRowText(String ...args) {
        return String.format(StringFormatTemplate.FORMAT_FILE, args);
    }

    private List<TodoItem> listAllFromFile(String fileName) {
        List<String> textList = FileUtils.readFile(config.getBasePath(), fileName);
        return todoReceiver.convertTodoList(textList);
    }



    private void cacheSingleFile() {
        User userByFile = userReceiver.findUserByName(UserSessionUtils.getUserNameBySession());
        String fileName = putFileToMap(userByFile);
        List<TodoItem> todoListByKey = todoReceiver.getTodoListByKey(userByFile.getId());
        if (todoListByKey.isEmpty()) {
            // 初始化内存
            todoReceiver.addAllByKey(userByFile.getId(), listAllFromFile(fileName));
        }
    }


    private void cacheAllFile() {
        // 读用户配置文件
        List<User> allUsers = userReceiver.findAllUsers();
        allUsers.forEach(user -> {
            String fileName = putFileToMap(user);
            // 初始化内存
            todoReceiver.addAllByKey(user.getId(), listAllFromFile(fileName));

        });
    }

    private String putFileToMap(User user) {
        String fileName = String.format(StringFormatTemplate.USER_FILE_NAME_FORMAT,
                user.getUserName(), config.getFileName());
        // 创建文件 并且向 map中put
        if (fileMap.get(user.getId()) == null) {
            fileMap.put(user.getId(), FileUtils.createFile(config.getBasePath(), fileName));
        }
        return fileName;
    }
}
