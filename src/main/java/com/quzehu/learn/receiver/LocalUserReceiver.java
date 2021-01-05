package com.quzehu.learn.receiver;

import com.quzehu.learn.api.Command;
import com.quzehu.learn.api.LoginStatus;
import com.quzehu.learn.api.UserReceiver;
import com.quzehu.learn.config.UserConfig;
import com.quzehu.learn.model.User;
import com.quzehu.learn.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 本地文件中的用户执行者
 *
 * @ProjectName 项目名称
 * @ClassName com.quzehu.learn.receiver.LocalUserReceiver
 * @Author Qu.ZeHu
 * @Date 2021/1/5 22:11
 * @Version 1.0
 */
@Component
public class LocalUserReceiver implements UserReceiver {

    private final UserConfig config;

    public LocalUserReceiver(UserConfig config) {
        this.config = config;
    }

    @Override
    public User findUserByName(String userName) {
        List<User> allUsers = findAllUsers();
        List<User> users = allUsers.stream()
                .filter(item -> userName.equals(item.getUserName())).collect(Collectors.toList());
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }


    @Override
    public List<User> findAllUsers() {
        URL url = getClass().getClassLoader().getResource(config.getFileName());
        String filePath = url.getFile();
        filePath  = filePath.replaceAll("%20", " ");
        List<String> userStrings = FileUtils.readFile(filePath);
        // 查找输入的用户 在不在系统用户列表中
        return userStrings.stream().map(userStr -> {
            User user = new User();
            String[] element = userStr.split(" ");
            user.setId(Integer.valueOf(element[0]));
            user.setUserName(element[1]);
            user.setPassword(element[2]);
            return user;
        }).collect(Collectors.toList());
    }
}
