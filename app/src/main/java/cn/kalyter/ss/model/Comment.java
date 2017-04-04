package cn.kalyter.ss.model;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer microblogId;

    private Integer userId;

    private Integer beCommentedId;

    private String content;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMicroblogId() {
        return microblogId;
    }

    public void setMicroblogId(Integer microblogId) {
        this.microblogId = microblogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBeCommentedId() {
        return beCommentedId;
    }

    public void setBeCommentedId(Integer beCommentedId) {
        this.beCommentedId = beCommentedId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}