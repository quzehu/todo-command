package com.quzehu.learn.constant;

/**
 * 命令定义枚举
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.constant.CommandEnum
 * @Author Qu.ZeHu
 * @Date 2021/1/10 16:51
 * @Version 1.0
 */
public enum CommandEnum {
    /**
     *
     */
    ADD(StringConstant.ADD_COMMAND, StringConstant.ADD_INPUT,
            "添加一个待办事项，<item1 item2 ...>为任意字符串，多个以空格分隔。"),

    DONE(StringConstant.DONE_COMMAND, StringConstant.DONE_INPUT,
            "完成一个待办事项，<itemIndex1 itemIndex2 ...>为待办事项的索引，多个以空格分隔。"),

    LIST(StringConstant.LIST_COMMAND, StringConstant.LIST_INPUT,
            "查看待办事项列表，默认查看所有未完成的待办事项，支持带选项查看。\n选项\n%s"),

    LOGIN(StringConstant.LOGIN_COMMAND, StringConstant.LOGIN_INPUT,
            "用户登录，user为系统内置用户，目前仅支持当前内置用户登录。\n选项\n%s"),

    LOGOUT(StringConstant.LOGOUT_COMMAND, StringConstant.LOGOUT_INPUT,
            "用户退出，退出当前登录的用户。"),

    EXPORT(StringConstant.EXPORT_COMMAND, StringConstant.EXPORT_INPUT,
            "导出文件到指定的目录，选项、文件名必填，文件名不需要加扩展名，" +
                    "默认导出.xlsx文件，默认的导出文件在./localFile/export目录下。\n选项\n%s"),

    IMPORT(StringConstant.IMPORT_COMMAND, StringConstant.IMPORT_INPUT,
            "导入文件中的内容，选项、文件名必填，文件名不需要加扩展名，" +
                    "仅支持导入.xlsx文件，需确保导入的文件在./localFile/import目录下。\n选项\n%s"),

    /*PASSWORD(StringConstant.PASSWORD_COMMAND, "",
            "输入密码。"),*/

    HELP(StringConstant.HELP_COMMAND,StringConstant.HELP_INPUT,
            "帮助命令，查看应用命令的帮助文档。\n选项\n%s"),

    EXIT(StringConstant.EXIT_COMMAND, StringConstant.EXIT_COMMAND, "退出应用命令。"),
    ;


    private String name;

    private String format;

    private String description;

    CommandEnum(String name, String format, String description) {
        this.name = name;
        this.format = format;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static CommandEnum valueOfByName(String name) {
        for (CommandEnum e : CommandEnum.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        String nameStr;
        if (StringConstant.EXIT_COMMAND.equals(name)) {
            nameStr = String.format(StringFormatTemplate.EXIT_COMMAND_NAME, name);
        } else {
            nameStr = String.format(StringFormatTemplate.COMMAND_NAME, name);
        }
        String formatStr = String.format(StringFormatTemplate.COMMAND_FORMAT, format);
        String descriptionStr = String.format(StringFormatTemplate.COMMAND_DESCRIPTION, description);
        return String.format(StringFormatTemplate.COMMAND_OUTPUT_FORMAT, nameStr, formatStr, descriptionStr);
    }

    public static void main(String[] args) {
        CommandEnum[] values = CommandEnum.values();
        for (CommandEnum e : values) {
            System.out.println(e.toString());
        }
    }
}
