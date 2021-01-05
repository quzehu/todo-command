package com.quzehu.learn.constant;

/**
 * 待办事件状态枚举
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.constant.ItemStatusEnum
 * @Author Qu.ZeHu
 * @Date 2021/1/3 16:28
 * @Version 1.0
 */
public enum ItemStatusEnum {


    /**
     * 待办
     */
    NOT_DONE(0,"not done",  "待办"),

    /**
     * 已完成
     */
    DONE(1, "done", "已完成");

    private Integer status;

    private String englishText;

    private String chineseText;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }

    public String getChineseText() {
        return chineseText;
    }

    public void setChineseText(String chineseText) {
        this.chineseText = chineseText;
    }

    ItemStatusEnum(Integer status, String englishText, String chineseText) {
        this.status = status;
        this.englishText = englishText;
        this.chineseText = chineseText;
    }



}
