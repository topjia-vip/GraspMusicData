package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.*;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.base.RedisExpirationDate;
import com.topjia.getqqyydata.entity.Singer;
import com.topjia.getqqyydata.service.SingerListService;
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
 * @date 2019-11-30 19:54
 */
@Slf4j
@Service
public class SingerListServiceImpl implements SingerListService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Singer> getSingerList() throws Exception {
        List<Singer> singerList = (List<Singer>) redisTemplate.opsForValue().get("SingerList");
        if (singerList != null) {
            return singerList;
        } else {
            singerList = getSingers();
            redisTemplate.opsForValue().set("SingerList", singerList, RedisExpirationDate.SINGER_LIST_TIME, TimeUnit.HOURS);
        }
        return singerList;
    }

    private List<Singer> getSingers() throws Exception {
        List<Singer> singerList;
        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg";
        String data = "{\"singerList\":{\"module\":\"Music.SingerListServer\",\"method\":\"get_singer_list\",\"param\":{\"area\":-100,\"sex\":-100,\"genre\":-100,\"index\":-100,\"sin\":0,\"cur_page\":1}}}";
        JSONObject object = JSONObject.parseObject(data);
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
                "data",
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
                JSON.toJSON(object),
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, null);
        JSONArray jsonArray = getRes.getJSONObject("singerList").getJSONObject("data").getJSONArray("singerlist");
        singerList = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            Singer singer = new Singer();
            singer.setSingerMid(obj.getString("singer_mid"));
            singer.setSingerName(obj.getString("singer_name"));
            singerList.add(singer);
        }
        return singerList;
    }
}
