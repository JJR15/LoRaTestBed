package com.project.service;

import com.project.model.Nodeinfo;
import com.project.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DongBaishun on 2017/7/10.
 */
public interface UserService {
    // 列出所有用户
    List<User> listUsers();

    // 检测登录/可能为null表示用户名不存在
    int loginCheck(String username, String passowrd);

    // 判断用户
    User selectUserByName(String username);

    // 查看所有用户
    List<User> selectUsers();

    // 增加用户
    boolean addUser(String username, String password);

    // 删除用户
    boolean deleteUser(String username);

    // 更新用户
    boolean updateUser(User user);

    // 批量更新用户
    List<String> updateUserBatch(List<User> users);

}
