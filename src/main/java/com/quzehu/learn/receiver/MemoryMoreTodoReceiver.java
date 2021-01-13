package com.quzehu.learn.receiver;

import com.alibaba.excel.EasyExcel;
import com.quzehu.learn.config.TodoConfig;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.constant.StringFormatTemplate;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.FileUtils;
import com.quzehu.learn.utils.UserSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 支持用户信息的内存接收者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.UserMemoryTodoReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/6 14:47
 * @Version 1.0
 */
@Component
@Lazy
public class MemoryMoreTodoReceiver extends AbstractMemoryTodoReceiver {

    @Autowired
    private TodoConfig todoConfig;


    @Override
    public List<TodoItem> list() {
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems = getTodoListByKey(userId);
        if (todoItems.isEmpty()) {
            return todoItems;
        }
        return filterNotDoneTodoList(todoItems);
    }

    @Override
    public List<TodoItem> list(String ...args) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems = getTodoListByKey(userId);
        switch (args[0]) {
            case "--all":
                return todoItems;
            case "--done":
                return filterDoneTodoList(todoItems);
            default:
                throw new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE);
        }
    }


    @Override
    public TodoItem valueOf(int index) {
        // 校验
        Integer userId = UserSessionUtils.getUserIdBySession();
        check(userId, index -1);
        return getTodoListByKey(userId).get(index - 1);
    }

    @Override
    public boolean done(int index) {
        // 校验
        Integer userId = UserSessionUtils.getUserIdBySession();
        check(userId, index -1);

        TodoItem todoItem = getTodoListByKey(userId).get(index - 1);
        todoItem.setStatus(ItemStatusEnum.DONE.getStatus());
        todoItem.setUpdateTime(new Date());
        return true;
    }

    @Override
    public int add(String text) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        int newIndex = getTodoListByKey(userId).size() + 1;
        addOneToMayByKey(userId, new TodoItem(newIndex, text, userId));
        return newIndex;
    }

    @Override
    public void exportFile(String... args) {
        // 选项选择
        List<TodoItem> todoItems = switchExportOptions(args[0]);

        if (StringConstant.EXPORT_TYPE_EXCEL.equals(todoConfig.getExportType())) {
            exportExcelFile(args[1], todoItems);
        } else {
            exportTxtFile(args[1], todoItems);
        }
    }

    private List<TodoItem> switchExportOptions(String arg) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        List<TodoItem> todoItems;
        switch (arg) {
            case "-a":
                todoItems = getTodoListByKey(userId);
                break;
            case "-n":
                todoItems = filterNotDoneTodoList(getTodoListByKey(userId));
                break;
            case "-d":
                todoItems = filterDoneTodoList(getTodoListByKey(userId));
                break;
            default:
                throw new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE);
        }
        return todoItems;
    }


    private void exportTxtFile(String fileName, List<TodoItem> todoItems) {
        String exportContent = getExportContent(todoItems);
        fileName = fileName + ".txt";
        File exportFile = FileUtils.createFile(todoConfig.getExportPath(), fileName);
        FileUtils.writeFile(exportFile, exportContent, false);
    }

    private void exportExcelFile(String fileName, List<TodoItem> todoItems) {
        fileName =  fileName + ".xlsx";
        File exportFile = FileUtils.createFile(todoConfig.getExportPath(), fileName);
        EasyExcel.write(exportFile, TodoItem.class).sheet("待办事项")
                .doWrite(converterExportList(todoItems));
    }

    private List<TodoItem> converterExportList(List<TodoItem> todoItems) {
        return todoItems.stream().peek(item -> {
            ItemStatusEnum itemStatusEnum = ItemStatusEnum.valueOfByStatus(item.getStatus());
            item.setStatusText(itemStatusEnum != null ? itemStatusEnum.getChineseText() : "");
            item.setUserName(UserSessionUtils.getUserNameBySession());
        }).collect(Collectors.toList());
    }


    private String getExportContent(List<TodoItem> todoItems) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(StringConstant.EXPORT_HEAD).append("\n");
        for (TodoItem todoItem : todoItems) {
            Integer index = todoItem.getIndexNum();
            String text = todoItem.getText();
            Integer status = todoItem.getStatus();
            ItemStatusEnum itemStatusEnum = ItemStatusEnum.valueOfByStatus(status);
            String statusChinese = itemStatusEnum == null ? "" : itemStatusEnum.getChineseText();
            String userName = UserSessionUtils.getUserNameBySession();
            String[] rowArray = new String[]{String.valueOf(index), text, statusChinese, userName};
            String row = String.format(StringFormatTemplate.FORMAT_FILE, rowArray);
            stringBuilder.append(row).append("\n");
        }
        return stringBuilder.toString();
    }


    @Override
    public void importFile(List<TodoItem> todoItems) {
        Integer userId = UserSessionUtils.getUserIdBySession();
        addAllToMapByKey(userId, converterImportList(todoItems));
    }


    @Override
    public void clearList() {
        Integer userId = UserSessionUtils.getUserIdBySession();
        clearListByKey(userId);
    }
}
