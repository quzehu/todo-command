package com.quzehu.learn.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 参数对象
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.model.Options
 * @Author Qu.ZeHu
 * @Date 2021/1/6 9:57
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class Options implements Serializable {

    /**
     * 所属命令
     */
    private String command;
    /**
     * 参数名称
     */
    private String optionsName;

    /**
     * 参数描述
     */
    private String description;

    @Override
    public String toString() {
        return "\t" + optionsName + "  " + description + "\n";
    }


}
