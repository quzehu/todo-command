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
//@Accessors(chain = true)
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

    @TableField
    @ExcelProperty(value = "索引")
    private Integer indexNum;

    /**
     * 文本
     */

    @TableField
    @ExcelProperty(value = "文本")
    private String text;

    /**
     * 状态
     */
    @ExcelIgnore
    @TableField
    private Integer status;

    @TableField(exist = false)
    @ExcelProperty(value = "状态")
    private String statusText;

    /**
     * 用户
     */
    @ExcelIgnore
    @TableField
    private Integer userId;

    @TableField(exist = false)
    @ExcelProperty(value = "用户")
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
