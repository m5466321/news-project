package com.example.manager.mapper;

import com.example.manager.entity.Char;
import com.example.manager.entity.News;
import com.example.manager.entity.OperateLog;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Mapper
public interface NewsMapper {
    @Select("select newId from news where newId=#{id}")
    String getNews(String id);
    @Insert("insert into news(newId,title,detailUrl,createTime,text,image) values(#{id},#{title},#{detailUrl},#{time},#{text},#{image})")
    void saveNews(String id, String title, String detailUrl, String time, String text,String image);
    @Select("select * from news order by createTime desc")
    List<News> getNewsAll();
    @Select("select detailUrl from news where id=#{id}")
    String getNewsUrl(Integer id);
    @Select("select * from news order by views desc limit 10")
    List<News> getNewsHot();
    @Select("select views from news where id=#{id}")
    Integer getViews(Integer id);
    @Update("update news set views=#{i} where id=#{id}")
    void updateViews(Integer id, int i);
    @Select("select * from news where id=#{id}")
    List<News> getNewsInfo(Integer id);
    @Select("select * from news order by views desc")
    List<News> getNewsAllView();
    @Select("select * from news order by thumbsUp desc")
    List<News> getNewsAllThumbsUp();
    @Select("select * from news order by comments desc")
    List<News> getNewsAllComeents();
    @Select("select * from news where title like CONCAT('%', #{keyword}, '%')  order by views desc")
    List<News> getNewsAllViewK(String keyword);
    @Select("select * from news where title like CONCAT('%', #{keyword}, '%')  order by thumbsUp desc")
    List<News> getNewsAllThumbsUpK(String keyword);
    @Select("select * from news where title like CONCAT('%', #{keyword}, '%')  order by comments desc")
    List<News> getNewsAllComeentsK(String keyword);
    @Select("select * from news where title like CONCAT('%', #{keyword}, '%')  order by createTime desc")
    List<News> getNewsAllK(String keyword);
    @Delete("delete from news where id=#{id}")
    void deleteNews(Integer id);
    @Select("select * from operatelog where operateContent='登录' and operateTime>#{day} and operateTime<#{now}")
    List<OperateLog> getUserLogin(LocalDateTime day,LocalDateTime now);
    @Select("select * from operatelog where operateTime>#{day}")
    List<OperateLog> getLogAll(LocalDateTime day);
    @Select("select * from operatelog where operateContent='评论' and operateTime>#{day} ")
    List<OperateLog> getCommentsNum(LocalDateTime day);
    @Select("select * from operatelog where operateContent='点赞' and operateTime>#{day} ")
    List<OperateLog> getThumbsUpNum(LocalDateTime day);
    @Select("select * from operatelog where operateContent='浏览新闻详情' and operateTime>#{day} ")
    List<OperateLog> getNewsNum(LocalDateTime day);
    @Select("select * from news where createTime>#{day}")
    List<News> getNewsAllNum(LocalDateTime day);
    @Select("select * from news")
    List<News> getNewsNum1();
    @Select("select * from news order by views desc limit 8")
    List<News> getNewsHotC();
    @Select("select * from chart where data=#{data}")
    List<Char> getChar(LocalDate data);
    @Insert("insert into chart(data,views,thumbsUp,news,comments) values(#{data},#{size},#{size1},#{size2},#{size3})")
    void insertChar(LocalDate data, int size, int size1, int size2, int size3);
    @Update("update chart set views=#{size},thumbsUp=#{size1},news=#{size2},comments=#{size3} where data=#{data}")
    void updateChar(LocalDate data, int size, int size1, int size2, int size3);
    @Select("select * from chart order by data desc limit 7")
    List<Char> getCharT();
}
