package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.*;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.base.RedisExpirationDate;
import com.topjia.getqqyydata.entity.RecommendPic;
import com.topjia.getqqyydata.service.RecommendPicService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wjh
 * @date 2019-11-30 19:44
 */
@Slf4j
@Service
public class RecommendPicServiceImpl implements RecommendPicService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<RecommendPic> getRecommend() throws Exception {
        ArrayList<RecommendPic> pic = (ArrayList<RecommendPic>) redisTemplate.opsForValue().get("recommendPic");
        if (pic != null) {
            return pic;
        } else {
            pic = getRecommendPics();
            redisTemplate.opsForValue().set("recommendPic", pic, RedisExpirationDate.RECOMMEND_PIC_TIME, TimeUnit.HOURS);
        }
        return pic;
    }

    private ArrayList<RecommendPic> getRecommendPics() throws Exception {
        ArrayList<RecommendPic> pic;
        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg";
        String data = "{'focus':{'module':'QQMusic.MusichallServer','method':'GetFocus','param':{}}}";
        JSONObject jsonObject = JSON.parseObject(data);
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "loginUin",
                "hostUin",
                "platform",
                "needNewCode",
                "data"
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                "0",
                "0",
                "yqq.json",
                "0",
                JSON.toJSON(jsonObject),
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject object = (JSONObject) HttpDelegate.sendGet(url, paramsList, null);
        JSONArray jsonArray = object.getJSONObject("focus").getJSONObject("data").getJSONArray("content");
        // 处理结果
        pic = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String jump_info = obj.getJSONObject("jump_info").getString("url");
            if (!jump_info.contains("https")) {
                jump_info = "https://y.qq.com/n/yqq/album/" + jump_info + ".html";
            }
            // 重新封装结果返回给前端
            RecommendPic recommendPic = new RecommendPic();
            recommendPic.setJumpInfo(jump_info);
            String pic_info = obj.getJSONObject("pic_info").getString("url");
            recommendPic.setPicInfo(pic_info);
            pic.add(recommendPic);
        }
        return pic;
    }
}
