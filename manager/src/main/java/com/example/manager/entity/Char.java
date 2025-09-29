package com.example.manager.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import java.time.LocalDate;

@Data
public class Char {
    private Integer id;
    private LocalDate data;
    private Integer views;
    private Integer comments;
    private Integer thumbsUp;
    private Integer news;
}
