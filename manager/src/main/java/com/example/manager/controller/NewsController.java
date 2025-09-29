package com.example.manager.controller;

import com.example.manager.aspect.SystemLogger;
import com.example.manager.entity.Char;
import com.example.manager.entity.News;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.Result;
import com.example.manager.service.NewsService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/News")
@CrossOrigin("*")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/getNewsAll")//新闻列表
    public Result<PageBean<News>> getNewAll(Integer pageNum,
                                            Integer pageSize){
        return Result.success(newsService.getNewsAll(pageNum,pageSize));
    }
    @SystemLogger(value = "浏览新闻详情")
    @GetMapping("/getNewsInfo")//新闻详情
    public Result<Map<String,Object>> getNewsInfo(String id){
        if (id.equals(":id")){
            id="1";
        }
        return Result.success(newsService.getNewsInfo(Integer.valueOf(id)));
    }
    @GetMapping("/getNewsHot")//热门新闻
    public Result<List<News>> getNewsHot(){
        return Result.success(newsService.getNewsHot());
    }
    @GetMapping("/list")//管理员端新闻列表
    public Result<PageBean<News>> list(Integer pageNum,
                                       Integer pageSize,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String type){
        if(keyword==null){
            keyword="1";
        }
        if(type==null){
            type="1";
        }
        return Result.success(newsService.list(pageNum,pageSize,keyword,type));
    }
    @DeleteMapping("/deleteNews")
    public Result deleteNews(Integer id){
        newsService.deleteNews(id);
        return Result.success("删除成功");
    }
    @GetMapping("/getNewsHotC")
    public Result<List<News>> getNewsHotC(){
        return Result.success(newsService.getNewsHotC());
    }
    @GetMapping("/getChart")
    public Result<List<Char>> getChart(){
        return Result.success(newsService.getChart());
    }
}
