package com.example.manager.entity;

import lombok.Data;

@Data
public class News {
    private int id;
    private String title;
    private String detailUrl;
    private String createTime;
    private String text;
    private String image;
    private String thumbsUp;
    private String comments;
    private String views;
}
