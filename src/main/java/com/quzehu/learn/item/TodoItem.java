package com.quzehu.learn.item;

import com.quzehu.learn.constant.ItemStatusEnum;
import lombok.Data;

import java.io.Serializable;

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
     * 索引
     */
    private Integer index;

    /**
     * 文本
     */
    private String text;

    /**
     * 状态
     */
    private ItemStatusEnum statusEnum;

    public TodoItem(Integer index, String text) {
        this.index = index;
        this.text = text;
        this.statusEnum = ItemStatusEnum.NOT_DONE;
    }

    @Override
    public String toString() {
        if (ItemStatusEnum.DONE.equals(statusEnum)) {
            return index + ". [Done] <" + text + ">";
        } else {
            return index + ". <" + text + ">";
        }
    }
}
