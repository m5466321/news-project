package com.example.manager.entity;

import lombok.Data;

@Data
public class ThumbsUp {
    private Integer newId;
    private Integer userId;
    private String thumbsUser;
    private String createTime;
}
