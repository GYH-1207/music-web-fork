package com.javaclimb.music.service;

import com.javaclimb.music.domain.Song;

import java.util.List;

/**
 * 歌曲service接口
 */
public interface SongService {
    /**
     * 增加
     */
    public boolean insert(Song song);

    /**
     * 修改
     */
    public boolean update(Song song);

    /**
     * 删除
     */
    public boolean delete(Integer id);

    /**
     * 根据主键查询整个对象(Client端)
     */
    public Song selectByPrimaryKeyClient(Integer userId, Integer id);

    /**
     *  根据主键查询整个对象
     */
    public Song selectByPrimaryKey(int id);

    /**
     * 查询所有歌曲
     */
    public List<Song> allSong();

    /**
     * 根据歌名精确查询列表
     */
    public List<Song> songOfName(String name);

    /**
     * 根据歌名模糊查询列表
     */
    public List<Song> likeSongOfName(String name);

    /**
     * 根据歌手id查询(Client)
     */
    public List<Song> songOfSingerIdClient(Integer userId, Integer singerId);

    /**
     * 根据歌手id查询
     */
    public List<Song> songOfSingerId(Integer singerId);

    public List<Song> test();
}
