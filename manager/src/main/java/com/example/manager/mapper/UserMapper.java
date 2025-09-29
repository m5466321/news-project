package com.example.manager.mapper;

import com.example.manager.entity.OperateLog;
import com.example.manager.entity.User;
import com.example.manager.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    @Insert("insert into user (username,password,name,email,phone,address) values(#{username},#{password},#{name},#{email},#{phone},#{address})")
    void register(User register);
    @Select("select * from user where id=#{id}")
    List<UserInfo> getUserInfo(Integer id);
    @Update("update user set name=#{name},email=#{email},phone=#{phone},address=#{address},role=#{role},status=#{status} where id=#{id}")
    void updateUserInfo(UserInfo user);
    @Delete("delete from user where id=#{id}")
    void deleteUser(Integer id);
    @Select("select * from user where username=#{username}")
    UserInfo findByUserInfo(String username);
    @Update("update user set avatar=#{avatarUrl} where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);
    @Insert("insert into operatelog (operateBy,operateContent,operateTime,operateException) values" +
            " (#{operateBy},#{operateContent},#{operateTime},#{operateException})")
    void add(OperateLog logger);
    @Select("select * from user")
    List<UserInfo> getUserInfoList();
    @Insert("insert into operatelog (operateBy,operateContent,operateTime,operateException) values" +
            " (#{username},'登录',#{now},'null')")
    void addOpLogin(String username, LocalDateTime now);
    @Update("update user set role=#{role} where id=#{id}")
    void changeRole(Integer id, Integer role);
}
