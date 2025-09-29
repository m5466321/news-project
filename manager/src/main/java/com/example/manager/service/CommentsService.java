package com.example.manager.service;

import com.example.manager.entity.Comments;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.ThumbsUp;

import java.util.List;

public interface CommentsService {
    void addComments(Comments comments);

    void thumbsUp(ThumbsUp thumbsUp);

    String getNewIdd(Integer newId, Integer userId);

    List<ThumbsUp> getThumbsUp(Integer newId);

    List<Comments> getComments(Integer newId);

    PageBean<Comments> getCommentsList(Integer pageSize, Integer pageNum);

    PageBean<ThumbsUp> getThumbsList(Integer pageSize, Integer pageNum);
}
