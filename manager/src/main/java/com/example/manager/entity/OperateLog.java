package com.example.manager.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperateLog {
    // 主键

    private Integer id;

    // 操作人
    private String operateBy;

    // 操作内容
    private String operateContent;

    // 操作时间
    private LocalDateTime operateTime;

    // 操作异常
    private String operateException;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperateBy() {
        return operateBy;
    }

    public void setOperateBy(String operateBy) {
        this.operateBy = operateBy;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public LocalDateTime getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(LocalDateTime operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateException() {
        return operateException;
    }

    public void setOperateException(String operateException) {
        this.operateException = operateException;
    }
}

