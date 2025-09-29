package com.example.manager.service;

import com.example.manager.entity.OperateLog;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.Result;

import java.time.LocalDateTime;

public interface OperateLogService {
    PageBean<OperateLog> getOperateLogList(Integer pageSize, Integer pageNum);

    PageBean<OperateLog> getOperateLogListT(Integer pageSize, Integer pageNum, String operateBy);
}
