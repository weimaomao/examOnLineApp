package com.starcity.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @author slx
* @date 2020-05-12
*/
class SimpleExamEmployee implements Serializable {
    private String id;
        /** 考试成绩 */
    private Double testScore;
     /** 考试合格(0不合格1合格) */
    private Boolean qualified;

    private Integer passCounter;
    // 考试结束时间
    private Date beginTime;
    // 考试时间计数
    private Date endTime;

    private String userId;

    public Double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public Boolean getQualified() {
        return qualified;
    }

    public void setQualified(Boolean qualified) {
        this.qualified = qualified;
    }

    public Integer getPassCounter() {
        return passCounter;
    }

    public void setPassCounter(Integer passCounter) {
        this.passCounter = passCounter;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
