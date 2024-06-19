package com.javaclimb.music.controller;


import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.domain.SongPlaySum;
import com.javaclimb.music.service.SongPlaySumService;
import com.javaclimb.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 歌曲播放量controller
 */
@RestController
@RequestMapping("/songPlaySum")
public class SongPlaySumController {

    @Autowired
    private SongPlaySumService songPlaySumService;

    /**
     * 添加数据
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addPlaySum(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //获取前端传来的参数
        String userId = request.getParameter("userId").trim();  //用户id
        String songId = request.getParameter("songId").trim(); //歌单id
        String songPlayCount = request.getParameter("songPlayCount").trim();; //播放量
        SongPlaySum songPlaySum = new SongPlaySum();
        songPlaySum.setUserId(Integer.parseInt(userId));
        songPlaySum.setSongId(Integer.parseInt(songId));
        songPlaySum.setSongPlayCount(Integer.parseInt(songPlayCount));
        boolean flag = songPlaySumService.insert(songPlaySum);
        if (flag) {
            jsonObject.put(Consts.CODE, 1);
            jsonObject.put(Consts.MSG, "播放量添加成功");
            return jsonObject;
        }
        jsonObject.put(Consts.CODE, 0);
        jsonObject.put(Consts.MSG, "播放量添加失败");
        return jsonObject;

    }


    /**
     * 根据用户id和歌曲id修改count
     */
    @RequestMapping(value = "/songPlayCountadd", method = RequestMethod.GET)
    public Object songPlayCountadd(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();  //用户id
        String songId = request.getParameter("songId").trim(); //歌单id
        boolean flag = songPlaySumService.updateSum(Integer.parseInt(userId), Integer.parseInt(songId));
        if (flag) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户id和歌曲id查询整个对象
     */
    @RequestMapping(value = "/isadd", method = RequestMethod.GET)
    public Object isadd(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();  //用户id
        String songId = request.getParameter("songId").trim(); //歌单id
        if(songPlaySumService.isadd(Integer.parseInt(userId), Integer.parseInt(songId))==null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 根据用户id查询整个对象
     */
    @RequestMapping(value = "/selectByUserId", method = RequestMethod.GET)
    public Object selectByUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId").trim();  //用户id
        if(songPlaySumService.selectByUserId(Integer.parseInt(userId))==null){
            return false;
        }else {
            return songPlaySumService.selectByUserId(Integer.parseInt(userId));
        }
    }
}
