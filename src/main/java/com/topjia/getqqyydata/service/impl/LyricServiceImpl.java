package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.entity.Lyric;
import com.topjia.getqqyydata.entity.RequestHeader;
import com.topjia.getqqyydata.service.LyricService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 21:52
 */
@Service
public class LyricServiceImpl implements LyricService {
    public Lyric getLyric(String songmid) throws Exception {
        String url = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg";
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/");
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "songmid",
                "platform",
                "hostUin",
                "needNewCode",
                "categoryId",
                "pcachetime",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                songmid,
                "yqq",
                "0",
                "0",
                "10000000",
                new Date(),
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject o = (JSONObject) HttpDelegate.sendGet(url, paramsList, header);
        Lyric lyric = new Lyric();
        lyric.setLyric(o.getString("lyric"));
        return lyric;
    }
}
