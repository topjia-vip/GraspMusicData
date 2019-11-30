package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.entity.HotKey;
import com.topjia.getqqyydata.service.HotKeyService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 22:18
 */
@Service
public class HotKeyServiceImpl implements HotKeyService {

    @Override
    public List<HotKey> getHotKey() throws Exception {
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
        ArrayList<HotKey> hotKeys = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            HotKey hotKey = new HotKey();
            hotKey.setHotKey(object.getString("k"));
            hotKeys.add(hotKey);
        }
        return hotKeys;
    }
}
