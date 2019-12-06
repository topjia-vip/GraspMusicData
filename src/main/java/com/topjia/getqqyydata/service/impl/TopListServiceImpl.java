package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.*;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.base.RedisExpirationDate;
import com.topjia.getqqyydata.entity.*;
import com.topjia.getqqyydata.service.TopListService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    public ArrayList<TopGroup> getTopList() throws Exception {
        ArrayList<TopGroup> top_song_list = (ArrayList<TopGroup>) redisTemplate.opsForValue().get("Top_Song_List");
        if (top_song_list != null) {
            return top_song_list;
        } else {
            top_song_list = getObjects();
            redisTemplate.opsForValue().set("Top_Song_List", top_song_list, RedisExpirationDate.TOP_LIST_TIME, TimeUnit.HOURS);
            return top_song_list;
        }
    }

    private ArrayList<TopGroup> getObjects() throws Exception {
        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg";
        String data = "{\"comm\":{\"ct\":24},\"toplist\":{\"module\":\"musicToplist.ToplistInfoServer\",\"method\":\"GetAll\",\"param\":{}}}";
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
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, null);
        JSONArray jsonArray = getRes.getJSONObject("toplist").getJSONObject("data").getJSONArray("group");
        ArrayList<TopGroup> topGroups = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonTopGroup = (JSONObject) o;
            TopGroup topGroup = new TopGroup();
            topGroup.setGroupId(jsonTopGroup.getString("groupId"));
            topGroup.setGroupName(jsonTopGroup.getString("groupName"));
            JSONArray toplist = jsonTopGroup.getJSONArray("toplist");
            ArrayList<Top> tops = new ArrayList<>();
            for (Object o1 : toplist) {
                JSONObject jsonTop = (JSONObject) o1;
                Top top = new Top();
                if (jsonTop.getString("topId").equals("201")) {
                    continue;
                }
                top.setPicUrl(jsonTop.getString("headPicUrl"));
                top.setTopId(jsonTop.getString("topId"));
                top.setTopTitle(jsonTop.getString("title"));
                top.setUpdateTips(jsonTop.getString("updateTips"));
                JSONArray song = jsonTop.getJSONArray("song");
                ArrayList<RankSong> rankSongs = new ArrayList<>();
                for (Object o2 : song) {
                    JSONObject jsonSong = (JSONObject) o2;
                    RankSong rankSong = new RankSong();
                    rankSong.setSingerName(jsonSong.getString("singerName"));
                    rankSong.setSongName(jsonSong.getString("title"));
                    if (!StringUtils.isEmpty(jsonSong.getString("albumMid"))) {
                        rankSong.setSongPic("https://y.gtimg.cn/music/photo_new/T002R500x500M000" + jsonSong.getString("albumMid") + ".jpg?max_age=2592000");
                    } else {
                        rankSong.setSongPic("https://y.gtimg.cn/mediastyle/yqq/extra/player_cover.png?max_age=31536000");
                    }
                    rankSongs.add(rankSong);
                }
                top.setRankSong(rankSongs);
                tops.add(top);
            }
            topGroup.setTopList(tops);
            topGroups.add(topGroup);
        }
        return topGroups;
    }
}
