package com.quzehu.learn.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 参数对象
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.model.Parameter
 * @Author Qu.ZeHu
 * @Date 2021/1/6 9:57
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class Parameter implements Serializable {

    /**
     * 所属命令
     */
    private String command;
    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数描述
     */
    private String description;


}
