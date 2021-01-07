package com.quzehu.learn.api;

import java.util.Objects;

/**
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.api.IfOrElse
 * @Author Qu.ZeHu
 * @Date 2021/1/7 17:16
 * @Version 1.0
 */
public interface IfOrElse {
    /**
     * 执行是否相等判断，并执行对应的动作
     * @Date 2021/1/7 17:35
     * @param v1 参数1
     * @param v2 参数2
     * @param equalsAction 相等的动作
     * @param notEqualsAction 不相等的动作
     * @Author Qu.ZeHu
     * @return void
     **/
    default <T> void ifPresentOrElse(T v1, T v2, Runnable equalsAction, Runnable notEqualsAction) {
        Objects.requireNonNull(v1);
        if (v1.equals(v2)) {
            equalsAction.run();
        } else {
            notEqualsAction.run();
        }
    }

}
