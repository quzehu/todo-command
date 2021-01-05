package com.quzehu.learn.api;

import com.quzehu.learn.model.UserStatus;

/**
 * 用户登录状态
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.api.LoginStatus
 * @Author Qu.ZeHu
 * @Date 2021/1/5 23:10
 * @Version 1.0
 */
public interface LoginStatus {

    default UserStatus getLoginStatus() {
        return UserStatus.getInstance();
    }

}
