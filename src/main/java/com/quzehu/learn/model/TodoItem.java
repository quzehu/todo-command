package com.quzehu.learn.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quzehu.learn.constant.ItemStatusEnum;
import com.quzehu.learn.constant.StringFormatTemplate;
import lombok.Data;
import lombok.experimental.Accessors;

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
@TableName(value = "todo_item")
@Accessors(chain = true)
public class TodoItem implements Serializable {

    /**
     * 主键
     */
    @ExcelIgnore
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 索引
     */
    @ExcelProperty(value = "索引", index = 0)
    @TableField
    private Integer indexNum;

    /**
     * 文本
     */
    @ExcelProperty(value = "文本", index = 1)
    @TableField
    private String text;

    /**
     * 状态
     */
    @ExcelIgnore
    @TableField
    private Integer status;

    @ExcelProperty(value = "状态", index = 2)
    @TableField(exist = false)
    private String statusText;

    /**
     * 用户
     */
    @ExcelIgnore
    @TableField
    private Integer userId;

    @ExcelProperty(value = "用户", index = 3)
    @TableField(exist = false)
    private String userName;

    /**
     * 创建时间
     */
    @ExcelIgnore
    @TableField
    private Date createTime;

    /**
     * 修改时间
     */
    @ExcelIgnore
    @TableField
    private Date updateTime;

    public TodoItem() {}

    public TodoItem(Integer indexNum, String text) {
        this.indexNum = indexNum;
        this.text = text;
        this.status = ItemStatusEnum.NOT_DONE.getStatus();
        this.createTime = new Date();
    }
    public TodoItem(Integer indexNum, String text, Integer userId) {
        this(indexNum, text);
        this.userId = userId;
    }

    public TodoItem(String id , Integer indexNum, String text, Integer userId) {
        this(indexNum, text);
        this.id = id;
        this.userId = userId;
    }



    @Override
    public String toString() {
        if (ItemStatusEnum.DONE.getStatus().equals(status)) {
            return String.format(StringFormatTemplate.DONE_FORMAT_CONSOLE, indexNum, text);
        } else {
            return String.format(StringFormatTemplate.ADD_FORMAT_CONSOLE, indexNum, text);
        }
    }
}
