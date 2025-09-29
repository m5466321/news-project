package com.example.manager.service.serviceImpl;

import com.example.manager.entity.*;
import com.example.manager.mapper.NewsMapper;
import com.example.manager.service.NewsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {
@Autowired
    private NewsMapper newsMapper;

    @Override
    public void saveNews(String id, String title, String detailUrl, String time, String text,String image) {
        String a=newsMapper.getNews(id);
        if(a==null) {
            newsMapper.saveNews(id, title, detailUrl, time, text,image);
        }
    }

    @Override
    public PageBean<News> getNewsAll(Integer pageNum, Integer pageSize) {
        PageBean<News> pb= new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<News> list = newsMapper.getNewsAll();
        Page<News> p=(Page<News>)list;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public Map<String, Object> getNewsInfo(Integer id) {
        String url = newsMapper.getNewsUrl(id);
        Map<String, Object> map = new HashMap<>();
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; SpringBootCrawler/1.0)")
                    .timeout(10000)
                    .get();

            Element script = doc.select("body").first();
            String jsonStr = script.text().replaceAll("<!--repaste.body.begin-->", "").replaceAll("<!--repaste.body.end-->", "");
            int index = jsonStr.lastIndexOf("点击收起全文");
            String jsonStr2=jsonStr.substring(0, index);
            String str1 = jsonStr2.substring(0, jsonStr2.indexOf("正在加载"));
            String str2 = jsonStr2.substring(str1.length()+4, jsonStr2.length());
            map.put("data",str2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer a=newsMapper.getViews(id);
        newsMapper.updateViews(id,a+1);
        List<News> list = newsMapper.getNewsInfo(id);
        map.put("news",list);

        return map;
    }

    @Override
    public List<News> getNewsHot() {
        return newsMapper.getNewsHot();
    }

    @Override
    public PageBean<News> list(Integer pageNum, Integer pageSize, String keyword, String type) {
        PageBean<News> pb= new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<News> list =new ArrayList<>();
        if (!keyword.equals("1")&&type.equals("1")) {
            list=newsMapper.getNewsAllK(keyword);
        }
        if(keyword.equals("1")&&type.equals("1")){
            list=newsMapper.getNewsAll();
        }
        if(type.equals("浏览量最多")){
            if(!keyword.equals("1")){
                list=newsMapper.getNewsAllViewK(keyword);
            }else {
                list=newsMapper.getNewsAllView();
            }
        }
        if(type.equals("点赞最多")){
            if(!keyword.equals("1")){
                list=newsMapper.getNewsAllThumbsUpK(keyword);
            }else {
                list=newsMapper.getNewsAllThumbsUp();
            }
        }
        if(type.equals("评论最多")){
            if(!keyword.equals("1")){
                list=newsMapper.getNewsAllComeentsK(keyword);
            }else {
                list=newsMapper.getNewsAllComeents();
            }
        }
        Page<News> p=(Page<News>)list;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void deleteNews(Integer id) {
        newsMapper.deleteNews(id);
    }

    @Override
    public Result<Map<String, Object>> getCount() {
        LocalDateTime now=LocalDateTime.now();
        LocalDateTime day = LocalDateTime.now(ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0);

        Map<String,Object> map=new HashMap<>();
        List<OperateLog> userLogin= newsMapper.getUserLogin(day,now);
        map.put("userLoginNumDay",userLogin.size());
        List<OperateLog> LogAll= newsMapper.getLogAll(day);
        map.put("logNumDay",LogAll.size());
        List<OperateLog> commentsNum= newsMapper.getCommentsNum(day);
        map.put("commentsNumDay",commentsNum.size());
        List<OperateLog> thumbsUpNum= newsMapper.getThumbsUpNum(day);
        map.put("thumbsUpNumDay",thumbsUpNum.size());
        List<OperateLog> viewsNum= newsMapper.getNewsNum(day);
        map.put("viewsNumDay",viewsNum.size());
        List<News> news =newsMapper.getNewsAllNum(day);
        map.put("newsNumDay",news.size());
        List<News> news1=newsMapper.getNewsNum1();
        map.put("newsNum",news1.size());
        LocalDate data=LocalDate.now();
        List<Char> charList=newsMapper.getChar(data);
        if (charList.isEmpty()){
            newsMapper.insertChar(data,viewsNum.size(),thumbsUpNum.size(),news.size(),commentsNum.size());
        }else {
            newsMapper.updateChar(data,viewsNum.size(),thumbsUpNum.size(),news.size(),commentsNum.size());
        }
        return Result.success(map);
    }

    @Override
    public List<News> getNewsHotC() {
        return newsMapper.getNewsHotC();
    }

    @Override
    public List<Char> getChart() {
        List<Char> charList=newsMapper.getCharT();
        Collections.reverse(charList);
        return charList;
    }
}
