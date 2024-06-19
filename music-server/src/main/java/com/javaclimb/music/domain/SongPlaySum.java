package com.javaclimb.music.domain;

import java.io.Serializable;

/**
 * 用户歌曲的播放量
 */
public class SongPlaySum implements Serializable {

    private Integer id;     //主键

    private Integer userId; //用户id

    private Integer songId; //歌单id

    private Integer songPlayCount; //播放次数


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
}
