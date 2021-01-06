package com.quzehu.learn.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录状态
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.model.UserSession
 * @Author Qu.ZeHu
 * @Date 2021/1/5 23:11
 * @Version 1.0
 */
@Data
public class UserSession implements Serializable {

    /**
     * 缓存登录用户
     */
    private User cacheUser;

    private Boolean loginStatus = false;
    /**
     * 是否是正常的状态（true 正常 false 退出）
     */
    private Boolean normalStatus = true;

    /**
     * 输入的密码次数
     */
    private Integer inPasswordCount = 0;

    /**
     * 是否是输入密码命令
     */
    private Boolean inPasswordStatus = false;

    private static UserSession instance = new UserSession();

    private UserSession() {

    }

    public static UserSession getInstance() {
        return instance;
    }
}