package com.example.manager.entity;

import lombok.Data;
@Data
public class User {
    private String id;
    private String username;
    private String password;

    private String name;//昵称
    private String email;
    private String phone;
    private String address;
    private String role; // 0:用户，1:管理员
    private String status;// 0:禁用，1:启用
    private String avatar;
}
