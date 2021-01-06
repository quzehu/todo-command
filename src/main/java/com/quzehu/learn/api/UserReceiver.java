package com.quzehu.learn.api;

import com.quzehu.learn.model.User;

import java.util.List;

/**
 * 接受用户命令的执行者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.api.UserReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/5 22:10
 * @Version 1.0
 */
public interface UserReceiver {
    /**
     * 查找用户根据用户名称
     * @param userName 用户名称
     * @return 用户
     */
    User findUserByName(String userName);

    /**
     * 查找所有用户
     * @return 用户列表
     */
    List<User> findAllUsers();
}
