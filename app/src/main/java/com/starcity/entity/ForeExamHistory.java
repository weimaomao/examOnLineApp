package com.starcity.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @author slx
* @date 2020-05-12
*/

public class ForeExamHistory implements Serializable {

    private String id;

    private String title;
    /** 考试成绩 */
    private Double testScore;
    /** 免考原因 */
    private String exemptReason;
    /** 是否免考 */
    private Boolean exempt;
    /** 是否删除 */
    private Boolean deleted;
    /** 和人员表管理的id */
    private String employeeId;
    /** 是否已补考 */
    private Boolean retaked;
    /** 考试次数 */
    private Integer testNum;

    /** 考试单位发布表id */
    private String examUnitPublishId;

    /** 考试合格(0不合格1合格) */
    private Boolean qualified;

    private Boolean trainQualified;
    private Integer status;
    /** 培训截至选修分 */
    private Double trainElectiveScore;
    /** 培训截至必须分 */
    private Double trainCompulsoryScore;

    private Double totalScore;
    /* 考试开始时间 */
    private Long beginTime;

    /* 考试结束时间 */
    private Long endTime;

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public String getExemptReason() {
        return exemptReason;
    }

    public void setExemptReason(String exemptReason) {
        this.exemptReason = exemptReason;
    }

    public Boolean getExempt() {
        return exempt;
    }

    public void setExempt(Boolean exempt) {
        this.exempt = exempt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Boolean getRetaked() {
        return retaked;
    }

    public void setRetaked(Boolean retaked) {
        this.retaked = retaked;
    }

    public Integer getTestNum() {
        return testNum;
    }

    public void setTestNum(Integer testNum) {
        this.testNum = testNum;
    }

    public String getExamUnitPublishId() {
        return examUnitPublishId;
    }

    public void setExamUnitPublishId(String examUnitPublishId) {
        this.examUnitPublishId = examUnitPublishId;
    }

    public Boolean getQualified() {
        return qualified;
    }

    public void setQualified(Boolean qualified) {
        this.qualified = qualified;
    }

    public Boolean getTrainQualified() {
        return trainQualified;
    }

    public void setTrainQualified(Boolean trainQualified) {
        this.trainQualified = trainQualified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getTrainElectiveScore() {
        return trainElectiveScore;
    }

    public void setTrainElectiveScore(Double trainElectiveScore) {
        this.trainElectiveScore = trainElectiveScore;
    }

    public Double getTrainCompulsoryScore() {
        return trainCompulsoryScore;
    }

    public void setTrainCompulsoryScore(Double trainCompulsoryScore) {
        this.trainCompulsoryScore = trainCompulsoryScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
