package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.base.RedisExpirationDate;
import com.topjia.getqqyydata.entity.HotKey;
import com.topjia.getqqyydata.service.HotKeyService;
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
 * @date 2019-11-30 22:18
 */
@Slf4j
@Service
public class HotKeyServiceImpl implements HotKeyService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<HotKey> getHotKey() throws Exception {
        List<HotKey> hotKeys = (List<HotKey>) redisTemplate.opsForValue().get("HotKeys");
        if (hotKeys != null) {
            return hotKeys;
        } else {
            hotKeys = getHotKeys();
            redisTemplate.opsForValue().set("HotKeys", hotKeys, RedisExpirationDate.HOT_KEY_TIME, TimeUnit.HOURS);
        }
        return hotKeys;
    }

    private List<HotKey> getHotKeys() throws Exception {
        List<HotKey> hotKeys;
        String url = "https://c.y.qq.com/splcloud/fcgi-bin/gethotkey.fcg";
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
                "1",
                "yqq.json",
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, null);
        JSONArray jsonArray = getRes.getJSONObject("data").getJSONArray("hotkey");
        hotKeys = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            HotKey hotKey = new HotKey();
            hotKey.setHotKey(object.getString("k"));
            hotKeys.add(hotKey);
        }
        return hotKeys;
    }
}
