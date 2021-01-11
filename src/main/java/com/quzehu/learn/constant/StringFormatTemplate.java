package com.quzehu.learn.constant;


/**
 * 字符串格式化模版
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.constant.StringFormatTemplate
 * @Author Qu.ZeHu
 * @Date 2021/1/4 14:58
 * @Version 1.0
 */
public class StringFormatTemplate {

    // "索引 文本 状态 用户 创建时间 修改时间q";
    public static final String FORMAT_FILE = "%s %s %s %s %s %s";

    public static final String ADD_FORMAT_CONSOLE = "%d. <%s>";

    public static final String DONE_FORMAT_CONSOLE = "%d. [done] <%s>";

    public static final String ADD_AFTER_FORMAT_CONSOLE = "Item <%d> added.";

    public static final String DONE_AFTER_FORMAT_CONSOLE = "Item <%d> done.";

    public static final String LIST_AFTER_FORMAT_CONSOLE = "Total: %d items.";

    public static final String LIST_ALL_AFTER_FORMAT_CONSOLE = "Total: %d items, %d item done.";

    public static final String USER_NO_EXIST_FORMAT_CONSOLE = "User %s does not exist.";

    public static final String PREFIX_FORMAT_CONSOLE = "%s #>:";

    public static final String USER_FILE_NAME_FORMAT = "%s-%s";

    public static final String COMMAND_OUTPUT_FORMAT =  "%s\n%s\n%s\n*****************************************************";

    public static final String COMMAND_NAME = "命令\n\t" + StringConstant.TODO_COMMAND + " %s";

    public static final String EXIT_COMMAND_NAME = "命令\n\t%s";

    public static final String COMMAND_FORMAT = "格式\n\t%s";

    public static final String COMMAND_DESCRIPTION = "描述\n\t%s";

}
