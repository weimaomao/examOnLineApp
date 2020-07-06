package com.starcity.entity;

import com.starcity.utils.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class QbQuestion {
    @Id(autoincrement = true)
    private Long questionId;
    @Unique
    private String id;
    private String paperId;
    private String title;
    private String typeCode;
    private Double score;
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> answers;
    private Integer sort;
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> studentAnswers;
    private Double studentScore;
    private Boolean bingo;
    private Boolean deleted;
    @Transient
    private List<QbQuestionOption> options;


    @Generated(hash = 350086625)
    public QbQuestion(Long questionId, String id, String paperId, String title,
            String typeCode, Double score, List<String> answers, Integer sort,
            List<String> studentAnswers, Double studentScore, Boolean bingo,
            Boolean deleted) {
        this.questionId = questionId;
        this.id = id;
        this.paperId = paperId;
        this.title = title;
        this.typeCode = typeCode;
        this.score = score;
        this.answers = answers;
        this.sort = sort;
        this.studentAnswers = studentAnswers;
        this.studentScore = studentScore;
        this.bingo = bingo;
        this.deleted = deleted;
    }

    @Generated(hash = 102788189)
    public QbQuestion() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<String> getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(ArrayList<String> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    public Double getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Double studentScore) {
        this.studentScore = studentScore;
    }

    public Boolean getBingo() {
        return bingo;
    }

    public void setBingo(Boolean bingo) {
        this.bingo = bingo;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<QbQuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<QbQuestionOption> options) {
        this.options = options;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setStudentAnswers(List<String> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
}
