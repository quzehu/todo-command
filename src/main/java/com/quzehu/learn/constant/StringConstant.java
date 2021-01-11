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

    public static final String FIRST_CONSTANT_CONSOLE = "\n\nPlease input command, " +
            "If you don't know how to use it, please enter 'todo help' for help.";

    public static final String TODO_COMMAND = "todo";

    public static final String TODO_ERROR_PROMPT_CONSOLE = "Please input 'todo' first.";

    public static final String TODO_ERROR_NO_PARAM_PROMPT_CONSOLE = "Parameters are required after todo.";

    public static final String TODO_ERROR_CONSOLE = "Command error.";

    public static final String TODO_ERROR_NOT_EXIST_CONSOLE = "Command not exist.";

    /**
     * **************************登录命令相关****************************
     */
    public static final String LOGIN_COMMAND = "login";
    /**
     * 登录命令错误提示
     */
    public static final String LOGIN_INPUT = "todo login -u user";


    public static final String LOGIN_ERROR_PROMPT_CONSOLE
            = "Please input '" + LOGIN_INPUT +"' user must be a system user!";

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
            "Please input '" + LOGIN_INPUT + "' in first, user must be a system user!";
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

    public static final String DONE_INPUT = "todo done <itemIndex1 itemIndex2 ...>";
    /**
     * 完成错误提示
     */
    public static final String DONE_ERROR_PROMPT_CONSOLE =
            "Please input '" + DONE_INPUT + "', itemIndex must be of integer type.";
    /**
     * 参数必须是数字提示
     */
    public static final String DONE_ERROR_PARAM_PROMPT_CONSOLE = "The parameters must be numbers.";

    /**
     * *********************添加命令相关****************************************
     */
    public static final String ADD_COMMAND = "add";

    public static final String ADD_INPUT = "todo add <item1 item2 ...>";
    /**
     * 添加错误提示
     */
    public static final String ADD_ERROR_PROMPT_CONSOLE =
            "Please input '" + ADD_INPUT + "' item is a nothing String!";

    /**
     * *********************列表命令相关****************************************
     */
    public static final String LIST_COMMAND = "list";

    public static final String LIST_INPUT = "todo list --选项";
    /**
     * 参数长度错误提示
     */
    public static final String LIST_ERROR_PARAM_LENGTH_PROMPT_CONSOLE
            = "The parameters length must be one!";

    /**
     * 参数无效错误提示
     */
    public static final String LIST_ERROR_PARAM_INVALID_PROMPT_CONSOLE = "The Options is invalid.";

    /**
     * 待办事项列表是空的提示
     */
    public static final String LIST_ERROR_EMPTY_PROMPT_CONSOLE = "Currently todoList have not elements.";

    /**
     * **********************登出命令相关***************************************
     */
    public static final String LOGOUT_COMMAND = "logout";
    /**
     * 登出成功提示
     */
    public static final String LOGOUT_SUCCESS_FORMAT_CONSOLE = "Logout success!";

    public static final String LOGOUT_INPUT = "todo logout";
    /**
     * 登出错误提示
     */
    public static final String LOGOUT_ERROR_PROMPT_CONSOLE =
            "Please input '"+ LOGOUT_INPUT +"', no parameters required.";

    /**
     * 本地文件加载时机 初始化加载
     */
    public static final String LOAD_INIT = "init";

    /**
     * 本地文件加载时机 懒加载
     */
    public static final String LOAD_LAZY = "lazy";

    /**
     * *******************退出程序命令相关******************************************
     */
    public static final String EXIT_COMMAND = "exit";

    public static final String EXIT_SUCCESS_CONSOLE = "exit success!";

    /**
     * *******************帮助命令相关********************************************
     */
    public static final String HELP_COMMAND = "help";

    public static final String HELP_INPUT = "todo help --选项";

    /**
     * ********************导出文件命令相关****************************************
     */
    public static final String EXPORT_COMMAND = "export";

    public static final String EXPORT_ERROR_PARAM_LENGTH_PROMPT_CONSOLE =  "The parameters length error!";

    public static final String EXPORT_SUCCESS_PROMPT_CONSOLE = "Export file success!";

    public static final String EXPORT_HEAD = "索引 文本 状态 所属用户";

    public static final String EXPORT_INPUT = "todo export -选项 文件名";


}
