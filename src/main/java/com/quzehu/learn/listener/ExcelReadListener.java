package com.quzehu.learn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.quzehu.learn.api.TodoReceiver;
import com.quzehu.learn.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel文件读监听器
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.listener.ExcelReadListener
 * @Author Qu.ZeHu
 * @Date 2021/1/12 9:48
 * @Version 1.0
 */
public class ExcelReadListener extends AnalysisEventListener<TodoItem> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;

    List<TodoItem> list = new ArrayList<>();

    private final TodoReceiver todoReceiver;

    public ExcelReadListener(TodoReceiver todoReceiver) {
        this.todoReceiver = todoReceiver;
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(TodoItem data, AnalysisContext context) {
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }
    /**
     * 加上存储数据库
     */
    private void saveData() {
        todoReceiver.importFile(list);
    }

}
