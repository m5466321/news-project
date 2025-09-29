package com.example.manager.service.serviceImpl;

import com.example.manager.entity.*;
import com.example.manager.mapper.UserMapper;
import com.example.manager.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(User register) {
        userMapper.register(register);
    }

    @Override
    public List<UserInfo> userInfo(Integer id) {
        return userMapper.getUserInfo(id);
    }

    @Override
    public void updateUserInfo(UserInfo user) {
        userMapper.updateUserInfo(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public UserInfo findByUserInfo(String username) {
        return userMapper.findByUserInfo(username);
    }

    @Override
    public void add(OperateLog logger) {
        userMapper.add(logger);
    }

    @Override
    public void updateAvatar(String avatarUrl,Integer id) {
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public PageBean<UserInfo> getUserInfo(Integer pageSize, Integer pageNum) {
        PageBean<UserInfo> pb= new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<UserInfo> list = userMapper.getUserInfoList();
        Page<UserInfo> p=(Page<UserInfo>)list;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void addOpLogin(String username) {
        LocalDateTime now=LocalDateTime.now();
        userMapper.addOpLogin(username,now);
    }

    @Override
    public void changeRole(Integer id, Integer role) {
        userMapper.changeRole(id,role);
    }

}
