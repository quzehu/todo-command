package com.quzehu.learn.api;

import java.util.function.Consumer;

/**
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.api.IfOrElse
 * @Author Qu.ZeHu
 * @Date 2021/1/7 17:16
 * @Version 1.0
 */
public interface IfOrElse  {

    /**
     * 判断是否，并执行对应的动作
     * @Date 2021/1/7 22:30
     * @param f 参数1
     * @param eAction 参数2
     * @param neAction 参数3
     * @Author Qu.ZeHu
     **/
    default void ifPresentOrElse(boolean f, Runnable eAction, Runnable neAction) {
        if (f) {
            eAction.run();
        } else {
            neAction.run();
        }
    }

    /**
     * 判断是否，并执行对应的动作
     * @Date 2021/1/14 12:58
     * @param f 参数1
     * @param p 参数2
     * @param eAction 参数3
     * @param neAction 参数4
     * @Author Qu.ZeHu
     * @return void
     **/
    default <P> void ifPresentOrElse(boolean f, P p, Consumer<P> eAction, Runnable neAction) {
        if (f) {
            eAction.accept(p);
        } else {
            neAction.run();
        }
    }
    /**
     *  判断是否，并执行对应的动作
     * @Date 2021/1/14 12:59
     * @param f 参数1
     * @param p 参数2
     * @param eAction 参数3
     * @param neAction 参数4
     * @Author Qu.ZeHu
     * @return void
     **/
    default <P> void ifPresentOrElse(boolean f, P p, Consumer<P> eAction, Consumer<P> neAction) {
        if (f) {
            eAction.accept(p);
        } else {
            neAction.accept(p);
        }
    }

    /**
     * 判断是否，并执行对应的动作
     * @Date 2021/1/8 14:53
     * @param f 参数1
     * @param eAction 是的执行动作
     * @Author Qu.ZeHu
     **/
    default void ifPresent(boolean f, Runnable eAction) {
        if (f) {
            eAction.run();
        }
    }


    /**
     * 判断是否，并执行对应的动作
     * @Date 2021/1/14 12:59
     * @param f 参数1
     * @param p 参数2
     * @param consumer 参数3
     * @Author Qu.ZeHu
     * @return boolean
     **/
    default <P> boolean ifPresent(boolean f, P p, Consumer<P> consumer) {
        if (f) {
            consumer.accept(p);
        }
        return f;
    }

    /**
     * 判断是否，并执行对应的动作
     * @Date 2021/1/14 12:59
     * @param f 参数1
     * @param neAction 参数2
     * @Author Qu.ZeHu
     **/
    default void orElse(boolean f, Runnable neAction) {
        if (!f) {
            neAction.run();
        }
    }

    /**
     * 判断是否，并执行对应的动作
     * @Date 2021/1/14 13:00
     * @param f 参数1
     * @param p 参数2
     * @param neAction 参数3
     * @Author Qu.ZeHu
     * @return boolean
     **/
    default <P> boolean orElse(boolean f,  P p, Consumer<P> neAction) {
        if (!f) {
            neAction.accept(p);
        }
        return f;
    }

}
