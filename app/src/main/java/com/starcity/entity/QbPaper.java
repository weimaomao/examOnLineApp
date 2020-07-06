package com.starcity.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class QbPaper {

    @Id(autoincrement = true)
    private Long paperId;
    @Unique
    private String id;
    private String title;
    private Double duration;
    private Double sumMark;
    private Double passMark;
    private double studentTotalScore;
    private int uptime;
    @Transient
    List<QbQuestion> questions;


    @Generated(hash = 1657200339)
    public QbPaper(Long paperId, String id, String title, Double duration,
            Double sumMark, Double passMark, double studentTotalScore, int uptime) {
        this.paperId = paperId;
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.sumMark = sumMark;
        this.passMark = passMark;
        this.studentTotalScore = studentTotalScore;
        this.uptime = uptime;
    }

    @Generated(hash = 1235963658)
    public QbPaper() {
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

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
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

    public double getStudentTotalScore() {
        return studentTotalScore;
    }

    public void setStudentTotalScore(double studentTotalScore) {
        this.studentTotalScore = studentTotalScore;
    }

    public int getUptime() {
        return uptime;
    }

    public void setUptime(int uptime) {
        this.uptime = uptime;
    }

    public List<QbQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QbQuestion> questions) {
        this.questions = questions;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }
}
