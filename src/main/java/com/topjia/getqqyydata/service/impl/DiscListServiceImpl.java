package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.entity.DiscList;
import com.topjia.getqqyydata.entity.RequestHeader;
import com.topjia.getqqyydata.service.DiscListService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 19:40
 */
@Service
public class DiscListServiceImpl implements DiscListService {

    @Override
    public List<DiscList> getDiscList() throws Exception {
        String url = "https://c.y.qq.com/splcloud/fcgi-bin/fcg_get_diss_by_tag.fcg";
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/");
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "platform",
                "hostUin",
                "sin",
                "ein",
                "sortId",
                "needNewCode",
                "categoryId",
                "rnd",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                "yqq.json",
                "0",
                "0",
                "29",
                "5",
                "0",
                "10000000",
                Math.random(),
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);

        // 处理请求结果
        JSONObject getRest = (JSONObject) HttpDelegate.sendGet(url, paramsList, header);
        JSONArray array = getRest.getJSONObject("data").getJSONArray("list");
        ArrayList<DiscList> res = new ArrayList<>();
        for (Object o : array) {
            JSONObject obj = (JSONObject) o;
            DiscList discList = new DiscList();
            discList.setDissId(obj.getString("dissid"));
            discList.setDissName(obj.getString("dissname"));
            discList.setImgUrl(obj.getString("imgurl"));
            discList.setName(obj.getJSONObject("creator").getString("name"));
            res.add(discList);
        }
        return res;
    }
}
