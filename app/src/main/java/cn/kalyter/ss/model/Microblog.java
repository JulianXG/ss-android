package cn.kalyter.ss.model;

import java.util.Date;
import java.util.List;

public class Microblog {
    private Integer id;

    private Integer userId;

    private String avatar;

    private String nickname;

    private Integer issolved;

    private Integer rootId;

    private Integer repostFlag;

    private Integer repostId;

    private String repostContent;

    private Microblog rootMicroblog;

    private String content;

    private Integer likeStatus;

    private String deviceName;

    private Integer collectCount;

    private Integer commentCount;

    private Integer repostCount;

    private Integer likeCount;

    private List<Image> mImages;

    private Long postTime;

    private String theme;

    private String location;

    private String tag;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public Microblog getRootMicroblog() {
        return rootMicroblog;
    }

    public void setRootMicroblog(Microblog rootMicroblog) {
        this.rootMicroblog = rootMicroblog;
    }

    public Integer getRepostFlag() {
        return repostFlag;
    }

    public void setRepostFlag(Integer repostFlag) {
        this.repostFlag = repostFlag;
    }

    public Integer getRepostId() {
        return repostId;
    }

    public void setRepostId(Integer repostId) {
        this.repostId = repostId;
    }

    public String getRepostContent() {
        return repostContent;
    }

    public void setRepostContent(String repostContent) {
        this.repostContent = repostContent;
    }

    public Integer getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        this.mImages = images;
    }

    public Integer getIssolved() {
        return issolved;
    }

    public void setIssolved(Integer issolved) {
        this.issolved = issolved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getRepostCount() {
        return repostCount;
    }

    public void setRepostCount(Integer repostCount) {
        this.repostCount = repostCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Long getPostTime() {
        return postTime;
    }

    public void setPostTime(Long postTime) {
        this.postTime = postTime;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
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
