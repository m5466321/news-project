package com.example.manager.controller;

import com.example.manager.aspect.SystemLogger;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.Result;
import com.example.manager.entity.ThumbsUp;
import com.example.manager.service.CommentsService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.manager.entity.Comments;

import javax.xml.stream.events.Comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Comments")
@CrossOrigin("*")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @SystemLogger(value = "评论")
    @PostMapping("/addComments")//添加评论
    public Result addComments(@RequestBody Comments comments){

        commentsService.addComments(comments);
        return Result.success("添加成功");
    }
    @SystemLogger(value = "点赞")
    @PostMapping("/addThumbsUp")//点赞
    public Result thumbsUp(@RequestBody ThumbsUp thumbsUp){
        Integer newId=thumbsUp.getNewId();
        Integer userId=thumbsUp.getUserId();
        String newIdd=commentsService.getNewIdd(newId,userId);
        if(newIdd!=null) {
            return Result.error("你已经点赞过该新闻");
        }else {
            commentsService.thumbsUp(thumbsUp);
            return Result.success("点赞成功");
        }
    }
    @SystemLogger(value = "获取点赞数")
    @GetMapping("/getThumbsUp")//获取点赞数
    public Result<List<ThumbsUp>> getThumbsUp(Integer newId){
        List<ThumbsUp> thumbsUpList=commentsService.getThumbsUp(newId);
        return Result.success(thumbsUpList);
    }
    @SystemLogger(value = "获取评论")
    @GetMapping("/getComments")//获取评论
    public Result<List<Comments>> getComments(String newId){
        if (newId.equals(":id")){
            newId="1";
        }
        List<Comments> commentsList=commentsService.getComments(Integer.valueOf(newId));
        return Result.success(commentsList);
    }
    @SystemLogger(value = "获取评论列表")
    @GetMapping("/getCommentsNum")//获取评论数
    public Result<PageBean<Comments>> getCommentsNum(Integer pageSize,
                                                    Integer pageNum){
        return Result.success(commentsService.getCommentsList(pageSize,pageNum));
    }
    @SystemLogger(value = "获取点赞列表")
    @GetMapping("/getThumbsUpNum")//获取评论数
    public Result<PageBean<ThumbsUp>> getThumbsNum(Integer pageSize,
                                                     Integer pageNum){
        return Result.success(commentsService.getThumbsList(pageSize,pageNum));
    }
}
