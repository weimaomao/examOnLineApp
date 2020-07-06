package com.starcity.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class QbQuestionOption {
    @Id(autoincrement = true)
    private Long questionOptionId;
    @Unique
    private String id;
    private String questionId;
    private String title;
    private String sort;
    private Boolean deleted;

    @Generated(hash = 987536872)
    public QbQuestionOption(Long questionOptionId, String id, String questionId,
            String title, String sort, Boolean deleted) {
        this.questionOptionId = questionOptionId;
        this.id = id;
        this.questionId = questionId;
        this.title = title;
        this.sort = sort;
        this.deleted = deleted;
    }

    @Generated(hash = 1351467058)
    public QbQuestionOption() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(Long questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
