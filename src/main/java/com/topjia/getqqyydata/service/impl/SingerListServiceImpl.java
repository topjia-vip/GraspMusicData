package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.*;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.entity.Singer;
import com.topjia.getqqyydata.service.SingerListService;
import com.topjia.getqqyydata.utils.ChineseCharacterUtil;
import com.topjia.getqqyydata.utils.HttpDelegate;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 19:54
 */
@Service
public class SingerListServiceImpl implements SingerListService {

    @Override
    public List<Singer> getSingerList() throws Exception {
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
        System.out.println(getRes);
        JSONArray jsonArray = getRes.getJSONObject("singerList").getJSONObject("data").getJSONArray("singerlist");
        ArrayList<Singer> res = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            Singer singer = new Singer();
            singer.setSingerMid(obj.getString("singer_mid"));
            String singer_name = obj.getString("singer_name");
            // 获取姓的首字母
            singer.setSingerName(singer_name);
            String firstName = singer_name.substring(0, 1);
            String index = ChineseCharacterUtil.getUpperCase(firstName, false);
            singer.setIndex(index);
            res.add(singer);
        }
        return res;
    }
}
