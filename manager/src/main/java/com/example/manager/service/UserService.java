package com.example.manager.service;

import com.example.manager.entity.OperateLog;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.User;
import com.example.manager.entity.UserInfo;

import java.util.List;

public interface UserService {
    User findByUserName(String username);

    void register(User register);

    List<UserInfo> userInfo(Integer id);

    void updateUserInfo(UserInfo user);

    void deleteUser(Integer id);

    UserInfo findByUserInfo(String username);

    void add(OperateLog logger);

    void updateAvatar(String avatarUrl,Integer id);

    PageBean<UserInfo> getUserInfo(Integer pageSize, Integer pageNum);

    void addOpLogin(String username);

    void changeRole(Integer id, Integer role);
}
