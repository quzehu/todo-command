package com.quzehu.learn.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录状态
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.model.UserStatus
 * @Author Qu.ZeHu
 * @Date 2021/1/5 23:11
 * @Version 1.0
 */
@Data
public class UserStatus implements Serializable {

    /**
     * 缓存登录用户
     */
    private User cacheUser;

    /**
     * 是否是正常的状态（true 正常 false 退出）
     */
    private Boolean status = true;

    /**
     * 输入的密码次数
     */
    private Integer passwordNum = 0;

    /**
     * 是否是输入密码命令
     */
    private Boolean inputPsCommand = false;

    private static UserStatus instance = new UserStatus();

    private UserStatus() {

    }

    public static UserStatus getInstance() {
        return instance;
    }
}
