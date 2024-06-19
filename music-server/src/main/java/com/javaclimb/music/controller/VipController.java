package com.javaclimb.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.javaclimb.music.dto.VipValidDto;
import com.javaclimb.music.service.VipService;
import com.javaclimb.music.utils.Consts;
import com.javaclimb.music.utils.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vip")
public class VipController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private VipService vipService;

    @GetMapping("/newVipInfo")
    public Object getNewVipInfo(HttpServletRequest httpServletRequest) {
        JSONObject jsonObject = new JSONObject();

        String userId = httpServletRequest.getParameter("userId");
        String validUntilKey = RedisPrefixUtils.generatorVipValidUntilKey(userId);

        //vip信息
        String validUntilData = stringRedisTemplate.opsForValue().get(validUntilKey);

        if (validUntilData == null) {
            jsonObject.put(Consts.CODE, 0);
            jsonObject.put(Consts.MSG, "vip已过期");
            return jsonObject;
        }

        jsonObject.put(Consts.CODE, 1);
        jsonObject.put(Consts.MSG, "vip信息返回成功");
        jsonObject.put("data", validUntilData);
        return jsonObject;
    }

    @GetMapping("/isVip")
    public Object isVip(int userId) {
        return vipService.isVip(userId);
    }
}
