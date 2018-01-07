package com.jiangbo.mall.vo;

import java.util.Date;

/**
 * @author Jiangbo Cheng on 2017/10/21.
 */

public class UserVO {

    private Integer id;

    private String name;

    private String password;

    private String email;

    private String mobile;

    private String question;

    private String answer;

    private Integer status;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public UserVO(Integer id, String name, String password, String email, String mobile, String question, String answer, Integer status, Integer role, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.question = question;
        this.answer = answer;
        this.status = status;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public UserVO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
