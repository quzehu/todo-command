package com.quzehu.learn.utils;

import com.quzehu.learn.model.User;
import com.quzehu.learn.model.UserSession;

/**
 * 用户状态工具类
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.utils.UserSessionUtils
 * @Author Qu.ZeHu
 * @Date 2021/1/7 9:14
 * @Version 1.0
 */
public class UserSessionUtils {

    /**
     * 得到用户会话
     * @Date 2021/1/7 10:03
     * @Author Qu.ZeHu
     * @return com.quzehu.learn.model.UserSession
     **/
    public static UserSession getUserSession() {
        return UserSession.getInstance();
    }

    /**
     * 从用户会话中得到缓存用户
     * @Date 2021/1/7 10:04
     * @Author Qu.ZeHu
     * @return com.quzehu.learn.model.User
     **/
    public static User getUserBySession() {
        return getUserSession().getCacheUser();
    }

    /**
     * 从用户会话中得到用户ID
     * @Date 2021/1/7 10:04
     * @Author Qu.ZeHu
     * @return java.lang.Integer
     **/
    public static Integer getUserIdBySession() {
        return getUserBySession().getId();
    }
    /**
     * 从用户会话中得到用户名称
     * @Date 2021/1/7 10:05
     * @Author Qu.ZeHu
     * @return java.lang.String
     **/
    public static String getUserNameBySession() {
        return getUserBySession().getUserName();
    }

    public static String getPasswordBySession() {
        return getUserBySession().getPassword();
    }

    public static Integer getPasswordCountBySession() {
        return getUserSession().getInPasswordCount();
    }

    /**
     * 缓存用户到会话中
     * @Date 2021/1/7 10:05
     * @param user 用户
     * @Author Qu.ZeHu
     * @return void
     **/
    public static void cacheLoginUserToSession(User user) {
        UserSession userSession = getUserSession();
        userSession.setCacheUser(user)
        .setInPasswordStatus(true);
    }
    /**
     * 清除登录用户从会话中
     * @Date 2021/1/7 10:06
     * @Author Qu.ZeHu
     * @return void
     **/
    public static void clearLoginUserOfSession() {
        UserSession userSession = getUserSession();
        // 清空数据
        userSession.setCacheUser(null)
        .setInPasswordCount(0)
        .setLoginStatus(false)
        .setInPasswordStatus(false)
        .setNormalStatus(true);
    }

    public static void login() {
        UserSession userSession = getUserSession();
        // 设置为已经登录状态
        userSession.setLoginStatus(true)
        // 清空是密码登录的状态
        .setInPasswordStatus(false);
    }

    public static void exit() {
        UserSession userSession = getUserSession();
        // 清空缓存数据
        userSession.setCacheUser(null)
        .setInPasswordCount(0)
        .setNormalStatus(false);
    }

    public static void passwordCountAddOne() {
        UserSession userSession = getUserSession();
        userSession.setInPasswordCount(userSession.getInPasswordCount() + 1);
    }



}
