package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.SongPlaySumMapper;
import com.javaclimb.music.domain.SongPlaySum;
import com.javaclimb.music.service.SongPlaySumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 歌曲service实现类
 */
@Service
public class SongPlaySumServiceImpl implements SongPlaySumService {
    @Autowired
    private SongPlaySumMapper songPlaySumMapper;
    /**
     * 增加
     *
     * @param songPlaySum
     */
    @Override
    public boolean insert(SongPlaySum songPlaySum) {
        return songPlaySumMapper.insert(songPlaySum)>0;
    }

    /**
     * 修改
     *
     * @param songPlaySum
     */
    @Override
    public boolean update(SongPlaySum songPlaySum) {
        return songPlaySumMapper.update(songPlaySum)>0;
    }

    /**
     * 根据用户id 歌曲id修改
     *
     * @param userId
     * @param songId
     */
    @Override
    public boolean updateSum(Integer userId,Integer songId) {
        return songPlaySumMapper.updateSum(userId,songId)>0;
    };

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songPlaySumMapper.delete(id)>0;
    }

    /**
     * 根据用户id和歌曲id查询
     */
    @Override
    public SongPlaySum isadd(Integer userId,Integer songId){
        return songPlaySumMapper.isadd(userId,songId);
    }

    /**
     * 根据用户id查询整个对象
     *
     * @param userId
     */
    @Override
    public List<SongPlaySum> selectByUserId(Integer userId) {
        return songPlaySumMapper.selectByUserId(userId);
    }

    /**
     * 查询所有
     */
    @Override
    public List<SongPlaySum> allSongPlaySum() {
        return songPlaySumMapper.allPlaySum();
    }

}
