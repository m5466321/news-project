package com.example.manager.service.serviceImpl;

import com.example.manager.entity.Comments;
import com.example.manager.entity.OperateLog;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.ThumbsUp;
import com.example.manager.mapper.CommentsMapper;
import com.example.manager.service.CommentsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;
    @Override
    public void addComments(Comments comments) {
        String avatar= commentsMapper.getAvatar(comments.getUserId());
        comments.setAvatar(avatar);
        commentsMapper.insert(comments);
        List<Comments> conmmentNum=commentsMapper.getAllComments(comments.getNewId());
        Integer num=conmmentNum.size();
        commentsMapper.saveCommentsNum(num,comments.getNewId());
    }

    @Override
    public void thumbsUp(ThumbsUp thumbsUp) {
        commentsMapper.thumbsUp(thumbsUp);
        List<ThumbsUp> thumbsUpsNum=commentsMapper.getAllThumbUp(thumbsUp.getNewId());
        Integer num=thumbsUpsNum.size();
        LocalDateTime now=LocalDateTime.now();
        commentsMapper.saveThumbUpNum(num,thumbsUp.getNewId(),now);
    }

    @Override
    public String getNewIdd(Integer newId, Integer userId) {
        return commentsMapper.getNewIdd(newId,userId);
    }

    @Override
    public List<ThumbsUp> getThumbsUp(Integer newId) {
        return commentsMapper.getAllThumbUp(newId);
    }

    @Override
    public List<Comments> getComments(Integer newId) {
        return commentsMapper.getAllComments(newId);
    }

    @Override
    public PageBean<Comments> getCommentsList(Integer pageSize, Integer pageNum) {
        PageBean<Comments> pb= new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Comments> list = commentsMapper.getCommentsList();
        Page<Comments> p=(Page<Comments>)list;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public PageBean<ThumbsUp> getThumbsList(Integer pageSize, Integer pageNum) {
        PageBean<ThumbsUp> pb= new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<ThumbsUp> list = commentsMapper.getThumbsUpList();
        Page<ThumbsUp> p=(Page<ThumbsUp>)list;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
