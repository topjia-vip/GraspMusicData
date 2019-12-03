package com.topjia.getqqyydata.service.impl;

import com.alibaba.fastjson.*;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.base.RedisExpirationDate;
import com.topjia.getqqyydata.entity.RequestHeader;
import com.topjia.getqqyydata.entity.Song;
import com.topjia.getqqyydata.service.SongPurlService;
import com.topjia.getqqyydata.utils.*;
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
 * @date 2019-11-30 20:07
 */
@Service
public class SongPurlServiceImpl implements SongPurlService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Song> getSongPurl(List<Song> songs, String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            // 搜索不缓存数据
            return getSongs(songs);
        } else {
            List<Song> songList = (List<Song>) redisTemplate.opsForValue().get(id + "_Songs");
            if (songList != null) {
                return songList;
            } else {
                songList = getSongs(songs);
                redisTemplate.opsForValue().set(id + "_Songs", songList, RedisExpirationDate.SONG_TIME, TimeUnit.MINUTES);
            }
            return songList;
        }
    }

    private List<Song> getSongs(List<Song> songs) throws Exception {
        List<Song> songList;
        String songmids = SongMidUtil.getSongMids(songs);
        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg";
        String data = "{\"req\":{\"module\":\"CDN.SrfCdnDispatchServer\",\"method\":\"GetCdnDispatch\",\"param\":{\"guid\":\"7275231575\",\"calltype\":0,\"userip\":\"\"}},\"req_0\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\",\"param\":{\"guid\":\"7275231575\",\"songmid\":" + songmids + ",\"songtype\":[0],\"uin\":\"1256957450\",\"loginflag\":1,\"platform\":\"20\"}}}";
        JSONObject parse = JSONObject.parseObject(data);
        RequestHeader header = new RequestHeader("u.y.qq.com", "https://u.y.qq.com/");
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
                JSON.toJSON(parse),
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        JSONObject getRes = (JSONObject) HttpDelegate.sendGet(url, paramsList, header);
        // 获取qq音乐服务器cdn
        JSONArray cdns = getRes.getJSONObject("req").getJSONObject("data").getJSONArray("sip");
        ArrayList<String> _CDN = new ArrayList<>();
        for (int i = 0; i < cdns.size(); i++) {
            String cdn = (String) cdns.get(i);
            if (cdn.contains("amobile")) {
                _CDN.add(cdn);
            }
        }
        int size = _CDN.size();
        JSONArray jsonArray = getRes.getJSONObject("req_0").getJSONObject("data").getJSONArray("midurlinfo");
        songList = new ArrayList<>();
        // 再次封装音乐，过滤掉不可播放的音乐
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String purl = obj.getString("purl");
            if (!StringUtils.isEmpty(purl)) {
                Song song = songs.get(i);
                song.setUrl(_CDN.get(i % size) + purl);
                songList.add(song);
            }
        }
        return songList;
    }
}
