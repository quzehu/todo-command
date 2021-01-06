package com.quzehu.learn.constant;

/**
 * 字符串常量
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.constant.StringConstant
 * @Author Qu.ZeHu
 * @Date 2021/1/6 9:42
 * @Version 1.0
 */
public class StringConstant {
    /**
     * 命令行前缀
     */
    public static final String PREFIX_CONSTANT_CONSOLE = "#>:";

    /**
     * **************************登录命令相关****************************
     */
    public static final String LOGIN_COMMAND = "login";
    /**
     * 登录命令错误提示
     */
    public static final String LOGIN_ERROR_PROMPT_CONSOLE
            = "Please input 'todo login -u user' user must be a system user!";

    /**
     * 登录后输入密码提示
     */
    public static final String LOGIN_PASSWORD_PROMPT_CONSOLE = "Password";


    /**
     * ************************密码命令相关*******************************
     */
    public static final String PASSWORD_COMMAND = "password";
    /**
     * 输入密码提示
     */
    public static final String PASSWORD_PROMPT_CONSOLE = "Please input a password!";
    /**
     * 密码错误提示
     */
    public static final String PASSWORD_ERROR_PROMPT_CONSOLE =
            "Please input 'todo login -u user' in first, user must be a system user!";
    /**
     * 登录成功提示
     */
    public static final String PASSWORD_SUCCESS_CONSOLE = "Login success!";
    /**
     * 密码错误，退出提示
     */
    public static final String PASSWORD_ERROR_EXIT_CONSOLE = "Password error, exit!";
    /**
     * 密码错误，再次输入提示
     */
    public static final String PASSWORD_ERROR_AGAIN_CONSOLE = "Password error, Please input again!";


    /**
     * *********************完成命令相关****************************************
     */
    public static final String DONE_COMMAND = "done";
    /**
     * 完成错误提示
     */
    public static final String DONE_ERROR_PROMPT_CONSOLE =
            "Please input 'todo done <itemIndex>', itemIndex must be of integer type.";

    /**
     * *********************添加命令相关****************************************
     */
    public static final String ADD_COMMAND = "add";
    /**
     * 添加错误提示
     */
    public static final String ADD_ERROR_PROMPT_CONSOLE =
            "Please input 'todo add <item>' item is a nothing String!";

    /**
     * *********************列表命令相关****************************************
     */
    public static final String LIST_COMMAND = "list";


    /**
     * **********************登出命令相关***************************************
     */
    public static final String LOGOUT_COMMAND = "logout";
    /**
     * 登出成功提示
     */
    public static final String LOGOUT_SUCCESS_FORMAT_CONSOLE = "Logout success!";
    /**
     * 登出错误提示
     */
    public static final String LOGOUT_ERROR_PROMPT_CONSOLE =
            "Please input 'todo logout', no parameters required.";




}
