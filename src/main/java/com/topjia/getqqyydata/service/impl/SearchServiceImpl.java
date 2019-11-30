package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.entity.*;
import com.topjia.getqqyydata.service.SearchService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import com.topjia.getqqyydata.utils.SingerFilterUtil;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wjh
 * @date 2019-11-30 22:24
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Override
    public HashMap<String, Object> search(String w, String p, String perpage, String n, String catZhida) throws Exception {
        String url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp";
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://y.qq.com/portal/search.html");
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "w",
                "p",
                "perpage",
                "n",
                "catZhida",
                "zhidaqu",
                "t",
                "flag",
                "ie",
                "sem",
                "remoteplace",
                "needNewCode",
                "platform",
                "uin",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                w,
                p,
                perpage,
                n,
                catZhida,
                "1",
                "0",
                "1",
                "utf-8",
                "1",
                "txt.mqq.all",
                "0",
                "0",
                "yqq.json",
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, null);
        JSONObject songinfo = getRes.getJSONObject("data").getJSONObject("song");
        JSONObject zhida = getRes.getJSONObject("data").getJSONObject("zhida");
        ArrayList<Object> list = new ArrayList<>();
        SearchResult searchResult = new SearchResult();
        searchResult.setSongs(songinfo);
        searchResult.setZhida(zhida);
        list.add(searchResult);
        JSONArray songs = songinfo.getJSONArray("list");
        ArrayList<Song> newSongs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            JSONObject obj = (JSONObject) songs.get(i);
            Song song = new Song();
            song.setId(obj.getString("songid"));
            song.setMid(obj.getString("songmid"));
            JSONArray singers = obj.getJSONArray("singer");
            song.setSinger(SingerFilterUtil.SingerFilter(singers));
            song.setName(obj.getString("songname"));
            String album = obj.getString("albumname");
            if (!StringUtils.isEmpty(album)) {
                song.setAlbum(album);
                song.setImage("https://y.gtimg.cn/music/photo_new/T002R300x300M000" + obj.getString("albummid") + ".jpg?max_age=2592000");
            }
            song.setDuration(obj.getString("interval"));
            newSongs.add(song);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("searchResult", searchResult);
        map.put("songs", newSongs);
        return map;
    }
}
