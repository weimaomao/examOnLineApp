package com.starcity.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ForeExamEmployee implements Parcelable {

    private String id;

    /** 考试名称 */
    private String title;

    /** 考试名称 */
    private String paperId;

    /** 考试成绩 */
    private String examTime;

    /*考试时长 */
    private Integer duration=1;

    /* 总分 */
    private Double sumMark=0D;

    /* 合格分 */
    private Double passMark=0D;

    /* 补考标识 */
    private Boolean supplyMark=false;

    /* 必修分 */
    private Double compulsoryScore=0D;

    /**
     * 考试状态  1、未考试  2、已经考试考试但是未开始
     */
    private Integer status=0;

    /* 选修分 */
    private Double electiveScore=0D;

    /* 培训截至必修分 */
    private Double trainCompulsoryScore=0D;

    /* 培训截至选修分 */
    private Double trainElectiveScore=0D;

    /* 培训结果 */
    private Boolean trainQualified=false;

    /**
     * 考试成绩
     */
    private Double testScore=0D;

    public Double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getSumMark() {
        return sumMark;
    }

    public void setSumMark(Double sumMark) {
        this.sumMark = sumMark;
    }

    public Double getPassMark() {
        return passMark;
    }

    public void setPassMark(Double passMark) {
        this.passMark = passMark;
    }

    public boolean isSupplyMark() {
        return supplyMark;
    }

    public void setSupplyMark(boolean supplyMark) {
        this.supplyMark = supplyMark;
    }

    public Double getCompulsoryScore() {
        return compulsoryScore;
    }

    public void setCompulsoryScore(Double compulsoryScore) {
        this.compulsoryScore = compulsoryScore;
    }

    public Double getElectiveScore() {
        return electiveScore;
    }

    public void setElectiveScore(Double electiveScore) {
        this.electiveScore = electiveScore;
    }

    public Double getTrainCompulsoryScore() {
        return trainCompulsoryScore;
    }

    public void setTrainCompulsoryScore(Double trainCompulsoryScore) {
        this.trainCompulsoryScore = trainCompulsoryScore;
    }

    public Double getTrainElectiveScore() {
        return trainElectiveScore;
    }

    public void setTrainElectiveScore(Double trainElectiveScore) {
        this.trainElectiveScore = trainElectiveScore;
    }

    public Boolean getTrainQualified() {
        return trainQualified;
    }

    public void setTrainQualified(Boolean trainQualified) {
        this.trainQualified = trainQualified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(paperId);
        parcel.writeString(examTime);
        parcel.writeInt(duration);
        parcel.writeDouble(sumMark);
        parcel.writeDouble(passMark);
        parcel.writeByte((byte)(supplyMark ?1:0));
        parcel.writeDouble(compulsoryScore);
        parcel.writeInt(status);
        parcel.writeDouble(electiveScore);
        parcel.writeDouble(trainCompulsoryScore);
        parcel.writeDouble(trainElectiveScore);
        parcel.writeByte((byte)(trainQualified ?1:0));
        parcel.writeDouble(testScore);
    }

    public static final Creator<ForeExamEmployee> CREATOR = new Creator<ForeExamEmployee>() {

        @Override
        public ForeExamEmployee createFromParcel(Parcel parcel) {
            ForeExamEmployee foreExamEmployee = new ForeExamEmployee();
            foreExamEmployee.id=parcel.readString();
            foreExamEmployee.title=parcel.readString();
            foreExamEmployee.paperId=parcel.readString();
            foreExamEmployee.examTime=parcel.readString();
            foreExamEmployee.duration=parcel.readInt();
            foreExamEmployee.sumMark=parcel.readDouble();
            foreExamEmployee.passMark=parcel.readDouble();
            foreExamEmployee.supplyMark=parcel.readByte()!=0;
            foreExamEmployee.compulsoryScore=parcel.readDouble();
            foreExamEmployee.status=parcel.readInt();
            foreExamEmployee.electiveScore=parcel.readDouble();
            foreExamEmployee.trainCompulsoryScore=parcel.readDouble();
            foreExamEmployee.trainElectiveScore=parcel.readDouble();
            foreExamEmployee.trainQualified=parcel.readByte()!=0;
            foreExamEmployee.testScore=parcel.readDouble();
            return foreExamEmployee;
        }

        @Override
        public ForeExamEmployee[] newArray(int i) {
            return new ForeExamEmployee[0];
        }
    };

}
