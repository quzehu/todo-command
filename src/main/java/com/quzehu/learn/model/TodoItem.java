package com.quzehu.learn.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringFormatTemplate;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 待办事项
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.item.TodoItem
 * @Author Qu.ZeHu
 * @Date 2021/1/3 16:18
 * @Version 1.0
 */

@Data
public class TodoItem implements Serializable {

    /**
     * 主键
     */
    @ExcelIgnore
    private Integer id;
    /**
     * 索引
     */
    @ExcelProperty(value = "索引", index = 0)
    private Integer index;

    /**
     * 文本
     */
    @ExcelProperty(value = "文本", index = 1)
    private String text;

    /**
     * 状态
     */
    @ExcelIgnore
    private Integer status;

    @ExcelProperty(value = "状态", index = 2)
    private String statusText;

    /**
     * 用户
     */
    @ExcelIgnore
    private Integer userId;

    @ExcelProperty(value = "用户", index = 3)
    private String userName;

    /**
     * 创建时间
     */
    @ExcelIgnore
    private Date createTime;

    /**
     * 修改时间
     */
    @ExcelIgnore
    private Date updateTime;

    public TodoItem() {}

    public TodoItem(Integer index, String text) {
        this.index = index;
        this.text = text;
        this.status = ItemStatusEnum.NOT_DONE.getStatus();
        this.createTime = new Date();
    }
    public TodoItem(Integer index, String text, Integer userId) {
        this(index, text);
        this.userId = userId;
    }

    public TodoItem(Integer id ,Integer index, String text, Integer userId) {
        this(index, text);
        this.id = id;
        this.userId = userId;
    }



    @Override
    public String toString() {
        if (ItemStatusEnum.DONE.getStatus().equals(status)) {
            return String.format(StringFormatTemplate.DONE_FORMAT_CONSOLE, index, text);
        } else {
            return String.format(StringFormatTemplate.ADD_FORMAT_CONSOLE, index, text);
        }
    }
}
