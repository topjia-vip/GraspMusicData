package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.base.RedisExpirationDate;
import com.topjia.getqqyydata.service.TopListService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wjh
 * @date 2019-11-30 21:59
 */
@Slf4j
@Service
public class TopListServiceImpl implements TopListService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public JSONArray getTopList() throws Exception {
        JSONArray top_song_list = (JSONArray) redisTemplate.opsForValue().get("Top_Song_List");
        if (top_song_list != null) {
            return top_song_list;
        } else {
            top_song_list = getObjects();
            redisTemplate.opsForValue().set("Top_Song_List", top_song_list, RedisExpirationDate.TOP_LIST_TIME, TimeUnit.HOURS);
            return top_song_list;
        }
    }

    private JSONArray getObjects() throws Exception {
        String url = "https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg";
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "uin",
                "needNewCode",
                "platform",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                "0",
                "01",
                "yqq.json",
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, null);
        return getRes.getJSONObject("data").getJSONArray("topList");
    }
}
