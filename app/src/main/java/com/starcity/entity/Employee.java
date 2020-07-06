package com.starcity.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Employee {
    private String id;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 人员状态
     */
    private Boolean status;
    /**
     * 总经验值
     */
    private Double empirical;

    /**
     * 今年经验值
     */
    private Double empiricalThisYear;
    /**
     * 真实姓名
     */
    private String actualName;

    private String sex;

    /**
     * 出生日期
     */
    private Long birthday;

    /**
     * 总积分
     */
    private Double score;
    /**
     * 今年积分
     */
    private Double scoreThisYear;
    /**
     * 完整度
     */
    private String complete;

    private String employer;

    /**
     * 证件照片
     */
    private String certPhotoUrl;

    /**
     * 身份证号码
     */
    private String certificateCode;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Double getEmpirical() {
        return empirical;
    }

    public void setEmpirical(Double empirical) {
        this.empirical = empirical;
    }

    public Double getEmpiricalThisYear() {
        return empiricalThisYear;
    }

    public void setEmpiricalThisYear(Double empiricalThisYear) {
        this.empiricalThisYear = empiricalThisYear;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getScoreThisYear() {
        return scoreThisYear;
    }

    public void setScoreThisYear(Double scoreThisYear) {
        this.scoreThisYear = scoreThisYear;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getCertPhotoUrl() {
        return certPhotoUrl;
    }

    public void setCertPhotoUrl(String certPhotoUrl) {
        this.certPhotoUrl = certPhotoUrl;
    }

    public String getCertificateCode() {
        return certificateCode;
    }

    public void setCertificateCode(String certificateCode) {
        this.certificateCode = certificateCode;
    }
}
