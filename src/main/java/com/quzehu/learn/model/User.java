package com.quzehu.learn.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.model.User
 * @Author Qu.ZeHu
 * @Date 2021/1/5 21:04
 * @Version 1.0
 */
@Data
public class User implements Serializable {

    private Integer id;

    private String userName;

    private String password;

}
