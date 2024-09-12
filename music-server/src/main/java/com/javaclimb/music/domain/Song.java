package com.javaclimb.music.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌曲
 */
public class Song implements Serializable {
    /*主键*/
    private Integer id;
//    歌手id
    private Integer singerId;
//    歌名
    private String name;
//    简介
    private String introduction;
//    上传时间
    private Date createTime;
//    更新时间
    private Date updateTime;
//    封面
    private String pic;
//    歌词
    private String lyric;
//    路径
    private String url;
//    是否vip歌曲
    private Integer isVip;
//    是否vip歌曲
    private Integer userId;
//    是否vip歌曲
    private Integer songId;
//    歌曲播放次数
    private Integer songPlayCount;
//    歌曲是否正在播放
    private Integer isActive = 0; //0未激活 1正在播放

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getSongPlayCount() {
        return songPlayCount;
    }

    public void setSongPlayCount(Integer songPlayCount) {
        this.songPlayCount = songPlayCount;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
