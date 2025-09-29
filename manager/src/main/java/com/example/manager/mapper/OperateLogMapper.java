package com.example.manager.mapper;

import com.example.manager.entity.OperateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OperateLogMapper {
    @Select("select * from operatelog")
    List<OperateLog> getLogAll();
    @Select("select * from operatelog where operateBy=#{operateBy}")
    List<OperateLog> getLogAllT(String operateBy);
}
