package com.example.manager.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comments {
    private Integer id;
    private Integer newId;
    private String comments;
    private Integer userId;
    private String createUser;
    private String avatar;
    private LocalDateTime createTime;
}