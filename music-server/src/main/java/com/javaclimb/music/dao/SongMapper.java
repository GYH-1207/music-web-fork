package com.javaclimb.music.dao;

import com.javaclimb.music.domain.Song;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌曲Dao
 */
@Repository
public interface SongMapper {
    /**
     * 增加
     */
    public int insert(Song song);

    /**
     * 修改
     */
    public int update(Song song);

    /**
     * 删除
     */
    public int delete(Integer id);

    /**
     * 根据主键查询整个对象(Client)
     */
//    public Song selectByPrimaryKeyClient(@Param("userId") Integer userId, @Param("id") Integer id);


    /**
     * 根据主键查询整个对象
     */
    public Song selectByPrimaryKey(Integer id);

    /**
     * 查询所有歌曲
     */
    public List<Song> allSong();

    /**
     * 根据歌歌曲名字模糊查询列表
     */
    public List<Song> songOfName(String name);

    /**
     * 根据歌名模糊查询列表
     */
    public List<Song> likeSongOfName(String name);

    /**
     * 根据歌手id查询(Client)
     */
//    public List<Song> songOfSingerIdClient(@Param("userId") Integer userId, @Param("singerId") Integer singerId);

    /**
     * 根据歌手id查询
     */
    public List<Song> songOfSingerId(@Param("singerId") Integer singerId);

    List<Song> test();
}
















