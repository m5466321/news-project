package com.example.manager.controller;
import com.example.manager.aspect.SystemLogger;
import com.example.manager.entity.OperateLog;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.Result;
import com.example.manager.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/operate")
@CrossOrigin("*")
public class OperateLogController {
    @Autowired
    private OperateLogService operateLogService;
    @SystemLogger(value = "获取日志")
    @GetMapping("/logList")
    public Result<PageBean<OperateLog>> getOperateLogList(Integer pageSize,
                                                          Integer pageNum,
                                                          @RequestParam(required = false)String operateBy){
        if (operateBy==null){
            return Result.success(operateLogService.getOperateLogList(pageSize,pageNum));
        }else {
            return Result.success(operateLogService.getOperateLogListT(pageSize,pageNum,operateBy));
        }
    }
}
