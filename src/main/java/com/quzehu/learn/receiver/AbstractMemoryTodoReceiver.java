package com.quzehu.learn.receiver;

import com.quzehu.learn.api.IfOrElse;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringConstant;
import com.quzehu.learn.model.TodoItem;
import com.quzehu.learn.utils.UserSessionUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象的内存接收者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.AbstractMemoryReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/5 14:54
 * @Version 1.0
 */

public abstract class AbstractMemoryTodoReceiver extends AdapterTodoReceiver implements IfOrElse {

    private final List<TodoItem> items = new ArrayList<>();

    private final Map<Integer, List<TodoItem>> itemsMap = new HashMap<>();

    /**
     * @param todoItems 待办事项列表
     * @Version 1.0
     */
    protected void addAll(List<TodoItem> todoItems) {
        items.addAll(todoItems);
    }

    /**
     * 获取内存中待办事项列表
     * @Date 2021/1/7 9:21
     * @Author Qu.ZeHu
     * @return java.util.List<com.quzehu.learn.model.TodoItem>
     * @Version 1.0
     **/
    protected List<TodoItem> getItems() {
        return items;
    }
    /**
     * 校验索引
     * @param index 索引
     * @Version V1.0
     */
    protected void check(int index) {
        check(items, index);
    }



    /**
     * 转化待办事项列表
     * @Date 2021/1/7 9:22
     * @param sourceList 源列表
     * @Author Qu.ZeHu
     * @return java.util.List<com.quzehu.learn.model.TodoItem>
     * @Version 2.0
     **/
    protected List<TodoItem> convertTodoList(List<String> sourceList) {
        return sourceList.stream().map(item -> {
            String[] arrays = item.split("\\s+");
            TodoItem todoItem = new TodoItem();
            if (arrays.length == 4) {
                todoItem.setIndexNum(Integer.valueOf(arrays[0]));
                todoItem.setText(arrays[1]);
                todoItem.setStatus(Integer.valueOf(arrays[2]));
                todoItem.setUserId(Integer.valueOf(arrays[3]));
            }
            return todoItem;
        }).collect(Collectors.toList());
    }


    /**
     * 添加待办事项到Map中根据键
     * @Date 2021/1/6 15:36
     * @param key 键
     * @param todoItem 待办事项
     * @Author Qu.ZeHu
     * @return void
     **/
    protected void addOneToMayByKey(Integer key, TodoItem todoItem) {
        List<TodoItem> todoItems = getTodoListByKey(key);
        todoItems.add(todoItem);
    }

    /**
     * 从Map中获得待办事项根据键
     * @Date 2021/1/6 15:34
     * @param key 键
     * @Author Qu.ZeHu
     * @return java.util.List<com.quzehu.learn.model.TodoItem>
     * @Version 2.0
     **/
    protected List<TodoItem> getTodoListByKey(Integer key) {
        itemsMap.computeIfAbsent(key, k -> new ArrayList<>());
        return itemsMap.get(key);
    }

    /**
     * 添加待办事项根据键
     * @Date 2021/1/7 9:23
     * @param key 键
     * @param todoItems 值
     * @Author Qu.ZeHu
     * @return void
     * @Version 2.0
     **/
    protected void addAllToMapByKey(Integer key, List<TodoItem> todoItems) {
        List<TodoItem> memoryItems = getTodoListByKey(key);
        memoryItems.addAll(todoItems);
    }

    /**
     * 校验索引
     * @param key 键
     * @param index 索引
     * @Version V2.0
     */
    protected void check(Integer key, int index) {
        List<TodoItem> todoItems = getTodoListByKey(key);
        check(todoItems, index);
    }


    /**
     * 校验索引
     * @param items 待办列表
     * @param index 索引
     * @Version V2.0
     */
    private void check(List<TodoItem> items, int index) {
        ifPresent(items.isEmpty(),
        () -> { throw new IllegalArgumentException(StringConstant.LIST_ERROR_EMPTY_PROMPT_CONSOLE); });
        ifPresent(index < 0 || index >= items.size(),
        () -> { throw new IllegalArgumentException(StringConstant.LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE); });
    }
    /**
     * 过滤已经完成的待办事项
     * @Date 2021/1/8 14:35
     * @param todoItems 待办事项
     * @Author Qu.ZeHu
     * @return java.util.List<com.quzehu.learn.model.TodoItem>
     **/
    protected List<TodoItem> filterDoneTodoList(List<TodoItem> todoItems) {
        return todoItems.stream()
                .filter(item -> ItemStatusEnum.DONE.getStatus().equals(item.getStatus()))
                .collect(Collectors.toList());
    }
    /**
     * 过滤待完成的待办事项
     * @Date 2021/1/8 14:35
     * @param todoItems 待办事项
     * @Author Qu.ZeHu
     * @return java.util.List<com.quzehu.learn.model.TodoItem>
     **/
    protected List<TodoItem> filterNotDoneTodoList(List<TodoItem> todoItems) {
        return todoItems.stream()
                .filter(item -> ItemStatusEnum.NOT_DONE.getStatus().equals(item.getStatus()))
                .collect(Collectors.toList());
    }
    /**
     * 清空待办事项根据key
     * @Date 2021/1/12 11:41
     * @param key 参数1
     * @Author Qu.ZeHu
     * @return void
     **/
    protected void clearListByKey(Integer key) {
        List<TodoItem> todoItems = itemsMap.get(key);
        if (!CollectionUtils.isEmpty(todoItems)) {
            todoItems.clear();
        }
    }

    protected List<TodoItem> converterImportList(List<TodoItem> todoItems) {
        return todoItems.stream().peek(item -> {
            ItemStatusEnum itemStatusEnum = ItemStatusEnum.valueOfByText(item.getStatusText());
            item.setStatus(itemStatusEnum != null ? itemStatusEnum.getStatus(): item.getStatus());
            item.setUserId(UserSessionUtils.getUserIdBySession());
            if (item.getCreateTime() == null) {
                item.setCreateTime(new Date());
            }
        }).collect(Collectors.toList());
    }


}
