package com.example.manager.mapper;

import com.example.manager.entity.Comments;
import com.example.manager.entity.ThumbsUp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CommentsMapper {
    @Insert("insert into comments (newId, comments, userId, createUser,avatar) values (#{newId}, #{comments}, #{userId}, #{createUser},#{avatar})")
    void insert(Comments comments);
    @Select("select * from comments where newId = #{newId}")
    List<Comments> getAllComments(Integer newId);
    @Update("update news set comments = #{num} where id = #{newId}")
    void saveCommentsNum(Integer num, Integer newId);
    @Insert("insert into thumbsUp (newId, userId, thumbsUser) values (#{newId}, #{userId}, #{thumbsUser})")
    void thumbsUp(ThumbsUp thumbsUp);
    @Select("select * from thumbsUp where newId = #{newId}")
    List<ThumbsUp> getAllThumbUp(Integer newId);
    @Update("update news set thumbsUp = #{num},createTime=#{now} where id = #{newId}")
    void saveThumbUpNum(Integer num, Integer newId, LocalDateTime now);
    @Select("select id from thumbsup where newId = #{newId} and userId = #{userId}")
    String getNewIdd(Integer newId, Integer userId);
    @Select("select avatar from user where id = #{userId}")
    String getAvatar(Integer userId);
    @Select("select * from comments")
    List<Comments> getCommentsList();
    @Select("select * from thumbsUp")
    List<ThumbsUp> getThumbsUpList();
}
