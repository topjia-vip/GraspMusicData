package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.entity.RequestHeader;
import com.topjia.getqqyydata.entity.Song;
import com.topjia.getqqyydata.service.CDInfoService;
import com.topjia.getqqyydata.utils.HttpDelegate;
import com.topjia.getqqyydata.utils.SingerFilterUtil;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 21:25
 */
@Service
public class CDInfoServiceImpl implements CDInfoService {

    @Override
    public List<Song> getCDInfo(String disstid) throws Exception {
        String url = "https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg";
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/");
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "type",
                "json",
                "utf8",
                "onlysong",
                "new_format",
                "disstid",
                "loginUin",
                "hostUin",
                "platform",
                "needNewCode",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                "1",
                "1",
                "1",
                "0",
                "1",
                disstid,
                "0",
                "0",
                "yqq.json",
                "0",
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, header);
        JSONObject cdlist = (JSONObject) getRes.getJSONArray("cdlist").get(0);
        JSONArray songlist = cdlist.getJSONArray("songlist");
        ArrayList<Song> songs = new ArrayList<>();
        for (Object o : songlist) {
            JSONObject obj = (JSONObject) o;
            Song song = new Song();
            song.setId(obj.getString("id"));
            song.setMid(obj.getString("mid"));
            JSONArray singers = obj.getJSONArray("singer");
            song.setSinger(SingerFilterUtil.SingerFilter(singers));
            song.setName(obj.getString("name"));
            JSONObject album = obj.getJSONObject("album");
            if (album != null) {
                song.setAlbum(album.getString("name"));
                song.setImage("https://y.gtimg.cn/music/photo_new/T002R300x300M000" + album.getString("mid") + ".jpg?max_age=2592000");
            }
            song.setDuration(obj.getString("interval"));
            songs.add(song);
        }
        return songs;
    }
}
