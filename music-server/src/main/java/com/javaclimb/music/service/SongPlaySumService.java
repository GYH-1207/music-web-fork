package com.javaclimb.music.service;

import com.javaclimb.music.domain.SongPlaySum;
import java.util.List;

/**
 * 歌曲service接口
 */
public interface SongPlaySumService {
    /**
     *增加
     */
    public boolean insert(SongPlaySum songPlaySum);

    /**
     *修改
     */
    public boolean update(SongPlaySum songPlaySum);

    /**
     * 根据用户id 歌曲id修改
     */
    public boolean updateSum(Integer userId,Integer songId);

    /**
     * 删除
     */
    public boolean delete(Integer id);


    /**
     * 根据用户id查询整个对象
     */
    public List<SongPlaySum> selectByUserId(Integer userId);

    /**
     * 查询所有
     */
    public List<SongPlaySum> allSongPlaySum();

    /**
     * 根据用户id和歌曲id查询
     */
    public SongPlaySum isadd(Integer userId, Integer songId);
}
