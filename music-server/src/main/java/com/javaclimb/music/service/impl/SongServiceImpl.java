package com.javaclimb.music.service.impl;

import com.javaclimb.music.dao.SongMapper;
import com.javaclimb.music.dao.SongPlaySumMapper;
import com.javaclimb.music.domain.Song;
import com.javaclimb.music.domain.SongPlaySum;
import com.javaclimb.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 歌曲service实现类
 */
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;

    @Autowired
    private SongPlaySumMapper songPlaySumMapper;

    /**
     * 增加
     *
     * @param song
     */
    @Override
    public boolean insert(Song song) {
        return songMapper.insert(song) > 0;
    }

    /**
     * 修改
     *
     * @param song
     */
    @Override
    public boolean update(Song song) {
        return songMapper.update(song) > 0;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public boolean delete(Integer id) {
        return songMapper.delete(id) > 0;
    }

    /**
     * 根据主键查询整个对象(Client)
     */
    @Override
    public Song selectByPrimaryKeyClient(Integer userId, Integer id) {
        Song song = songMapper.selectByPrimaryKey(id);
        List<SongPlaySum> songPlaySums = songPlaySumMapper.allPlaySum();

        //拼接songPlayCount到song对象然后返回
        for (SongPlaySum songPlaySum : songPlaySums) {
            if(song == null || songPlaySum == null) {
                continue;
            }
            if(song.getId().equals(songPlaySum.getSongId()) &&
                    Objects.equals(songPlaySum.getUserId(), userId)) {
                song.setSongId(song.getId());
                song.setUserId(songPlaySum.getUserId());
                song.setSongPlayCount(songPlaySum.getSongPlayCount());
                break;
            }
        }
        if (song != null && song.getSongPlayCount() == null) {
            song.setSongPlayCount(0);
            song.setUserId(-1);
            song.setSongId(song.getId());
        }
        return song;
    }

    /**
     * 根据主键查询整个对象
     */
    @Override
    public Song selectByPrimaryKey(int id) {
        Song song = songMapper.selectByPrimaryKey(id);
        if (song.getSongPlayCount() == null) {
            song.setSongId(song.getId());
            song.setUserId(-1);
            song.setSongPlayCount(0);
        }
        return song;

    }

    /**
     * 查询所有歌曲
     */
    @Override
    public List<Song> allSong() {
        return songMapper.allSong();
    }

    /**
     * 根据歌名精确查询列表
     *
     * @param name
     */
    @Override
    public List<Song> songOfName(String name) {
        return songMapper.songOfName(name);
    }

    /**
     * 根据歌名模糊查询列表
     *
     * @param name
     */
    @Override
    public List<Song> likeSongOfName(String name) {
        return songMapper.likeSongOfName("%" + name + "%");
    }


    /**
     * 根据歌手id查询(Client)
     */
    public List<Song> songOfSingerIdClient(Integer userId, Integer singerId) {
        List<Song> songs = songMapper.songOfSingerId(singerId);
        List<SongPlaySum> songPlaySums = songPlaySumMapper.allPlaySum();

        // 使用哈希表加速查找
        Map<Integer, SongPlaySum> playSumMap = new HashMap<>();
        for (SongPlaySum playSum : songPlaySums) {
            playSumMap.put(playSum.getSongId(), playSum);
        }

        List<Song> resList = new ArrayList<>();
        for (Song song : songs) {
            SongPlaySum playSum = playSumMap.get(song.getId());
            if (playSum != null && Objects.equals(playSum.getUserId(), userId)) {
                song.setUserId(userId);
                song.setSongId(song.getId());
                song.setSongPlayCount(playSum.getSongPlayCount());
            } else {
                song.setSongPlayCount(0);
                song.setUserId(-1);
                song.setSongId(song.getId());
            }
            resList.add(song);
        }
        return resList;
    }

    /**
     * 根据歌手id查询
     */
    @Override
    public List<Song> songOfSingerId(Integer singerId) {
        List<Song> songs = songMapper.songOfSingerId(singerId);
        for (Song song : songs) {
            if (song.getSongPlayCount() == null) {
                song.setSongId(song.getId());
                song.setUserId(-1);
                song.setSongPlayCount(0);
            }
        }
        return songs;
    }

    @Override
    public List<Song> test() {
        return songMapper.test();
    }
}
