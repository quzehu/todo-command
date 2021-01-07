package com.quzehu.learn.api;
import java.util.Objects;
import java.util.function.Consumer;

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
     * @param eAction 相等的动作
     * @param neAction 不相等的动作
     * @Author Qu.ZeHu
     * @return void
     **/
    default <T> void ifPresentOrElse(T v1, T v2, Runnable eAction, Runnable neAction) {
        Objects.requireNonNull(v1);
        if (v1.equals(v2)) {
            eAction.run();
        } else {
            neAction.run();
        }
    }

    /**
     * 执行是否相等判断，并执行对应的动作
     * @Date 2021/1/7 21:24
     * @param v1 参数1
     * @param v2 参数2
     * @param param 参数3
     * @param eAction 相等的动作
     * @param neAction 不相等的动作
     * @Author Qu.ZeHu
     * @return void
     **/
    default <T, P> void ifPresentOrElse(T v1, T v2, P param, Consumer<P> eAction, Runnable neAction) {
        Objects.requireNonNull(v1);
        if (v1.equals(v2)) {
            eAction.accept(param);
        } else {
            neAction.run();
        }
    }



    /**
     * 执行是否相等判断，并执行对应的动作
     * @Date 2021/1/7 17:55
     * @param v1 参数1
     * @param v2 参数2
     * @param eAction 相等的动作
     * @Author Qu.ZeHu
     * @return void
     **/
    default <T> void ifPresent(T v1, T v2, Runnable eAction) {
        Objects.requireNonNull(v1);
        if (v1.equals(v2)) {
            eAction.run();
        }
    }

    /**
     * 执行是否相等判断，并执行对应的动作
     * @Date 2021/1/7 17:56
     * @param v1 参数1
     * @param v2 参数2
     * @param neAction 不相等的动作
     * @Author Qu.ZeHu
     * @return void
     **/
    default <T> void orElse(T v1, T v2, Runnable neAction) {
        Objects.requireNonNull(v1);
        if (!v1.equals(v2)) {
            neAction.run();
        }
    }


}
