package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.entity.Song;
import com.topjia.getqqyydata.service.TopMusicListService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import com.topjia.getqqyydata.utils.SingerFilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 22:11
 */
@Slf4j
@Service
public class TopMusicListServiceImpl implements TopMusicListService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Object> getTopMusicList(String topid) throws Exception {
        List<Object> flagList = new ArrayList<>();
        List<Song> songList = (List<Song>) redisTemplate.opsForValue().get(topid + "_Songs");
        if (songList != null) {
            log.info("命中redis缓存,{}", songList);
            flagList.add(songList);
            flagList.add(true);
            return flagList;
        } else {
            songList = getTopSongs(topid);
            flagList.add(songList);
            flagList.add(false);
            return flagList;
        }
    }

    private List<Song> getTopSongs(String topid) throws Exception {
        String url = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_toplist_cp.fcg";
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "topid",
                "needNewCode",
                "uin",
                "tpl",
                "page",
                "type",
                "platform",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                topid,
                "1",
                "0",
                "3",
                "detail",
                "top",
                "yqq.json",
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, null);
        JSONArray jsonArray = getRes.getJSONArray("songlist");
        ArrayList<Song> songs = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            JSONObject musicData = obj.getJSONObject("data");
            Song song = new Song();
            song.setId(musicData.getString("songid"));
            song.setMid(musicData.getString("songmid"));
            JSONArray singers = musicData.getJSONArray("singer");
            song.setSinger(SingerFilterUtil.SingerFilter(singers));
            song.setName(musicData.getString("songname"));
            String album = musicData.getString("albumname");
            if (!StringUtils.isEmpty(album)) {
                song.setAlbum(album);
                song.setImage("https://y.gtimg.cn/music/photo_new/T002R300x300M000" + musicData.getString("albummid") + ".jpg?max_age=2592000");
            }
            song.setDuration(musicData.getString("interval"));
            songs.add(song);
        }
        return songs;
    }
}
