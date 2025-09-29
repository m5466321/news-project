package com.example.manager.service.serviceImpl;

import com.example.manager.entity.News;
import com.example.manager.entity.OperateLog;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.Result;
import com.example.manager.mapper.OperateLogMapper;
import com.example.manager.service.OperateLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageBean<OperateLog> getOperateLogList(Integer pageSize, Integer pageNum) {
        PageBean<OperateLog> pb= new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<OperateLog> list = operateLogMapper.getLogAll();
        Page<OperateLog> p=(Page<OperateLog>)list;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public PageBean<OperateLog> getOperateLogListT(Integer pageSize, Integer pageNum, String operateBy) {
        PageBean<OperateLog> pb= new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<OperateLog> list = operateLogMapper.getLogAllT(operateBy);
        Page<OperateLog> p=(Page<OperateLog>)list;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
