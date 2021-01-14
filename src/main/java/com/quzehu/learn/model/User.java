package com.quzehu.learn.model;

import com.quzehu.learn.constant.StringFormatTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@AllArgsConstructor
public class User implements Serializable {

    public User() {}

    private Integer id;

    private String userName;

    private String password;

    @Override
    public String toString() {
        return String.format(StringFormatTemplate.USER_FORMAT, id, userName, password);
    }
}
