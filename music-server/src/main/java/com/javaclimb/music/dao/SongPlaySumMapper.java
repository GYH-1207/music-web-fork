package com.javaclimb.music.dao;


import com.javaclimb.music.domain.SongPlaySum;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 歌曲Dao
 */
@Repository
public interface SongPlaySumMapper {
    /**
     *增加
     */
    public int insert(SongPlaySum songPlaySum);

    /**
     *修改
     */
    public int update(SongPlaySum songPlaySum);

    /**
     * 根据用户id 歌曲id修改
     */
    public int updateSum(Integer userId,Integer songId);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据用户id和歌曲id查询
     */
    public SongPlaySum isadd(Integer userId,Integer songId);

    /**
     * 根据用户id查询整个对象
     */
    public List<SongPlaySum> selectByUserId(Integer userId);

    /**
     * 查询所有
     */
    public List<SongPlaySum> allPlaySum();

}
















