package com.example.manager.service;


import com.example.manager.entity.Char;
import com.example.manager.entity.News;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.Result;

import java.util.List;
import java.util.Map;


public interface NewsService {
    void saveNews(String id, String title, String detailUrl, String time, String text,String iamge);

    PageBean<News> getNewsAll(Integer pageNum, Integer pageSize);

    Map<String, Object> getNewsInfo(Integer id);

    List<News> getNewsHot();

    PageBean<News> list(Integer pageNum, Integer pageSize, String keyword, String type);

    void deleteNews(Integer id);

    Result<Map<String, Object>> getCount();

    List<News> getNewsHotC();

    List<Char> getChart();
}
