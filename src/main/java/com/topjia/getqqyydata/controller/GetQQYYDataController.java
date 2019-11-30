package com.topjia.getqqyydata.controller;

import com.alibaba.fastjson.*;
import com.topjia.getqqyydata.base.BaseParamsAndValues;
import com.topjia.getqqyydata.bean.RequestHeader;
import com.topjia.getqqyydata.httpUtiles.HttpDelegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 获取QQ音乐接口数据的controller
 *
 * @author wjh
 * @date 2019-11-20 16:30
 */
@Slf4j
@RestController
@CrossOrigin // 允许跨域访问
public class GetQQYYDataController {
    /**
     * 获取qq音乐的推荐图片slider
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getRecommend")
    public Object getRecommend(@RequestBody String data) throws Exception {
        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg";
        JSONObject parse = JSONObject.parseObject(data);
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
                parse.get("loginUin"),
                parse.get("hostUin"),
                parse.get("platform"),
                parse.get("needNewCode"),
                parse.get("data"),
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, null);
    }

    /**
     * 获取qq音乐的推荐歌单列表
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getDiscList")
    public Object getDiscList(String platform, String sin, String ein, String sortId, String needNewCode, String categoryId, String rnd, String hostUin) throws Exception {
        String url = "https://c.y.qq.com/splcloud/fcgi-bin/fcg_get_diss_by_tag.fcg";
        RequestHeader header = new RequestHeader("c.y.qq.com", "https://c.y.qq.com/");
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "platform",
                "sin",
                "ein",
                "sortId",
                "needNewCode",
                "categoryId",
                "rnd",
                "hostUin",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                platform,
                sin,
                ein,
                sortId,
                needNewCode,
                categoryId,
                rnd,
                hostUin,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, header);
    }

    /**
     * 获取qq音乐歌手列表
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getSingerList")
    public Object getSingerList(String channel, String page, String key, String pagesize, String pagenum, String hostUin, String needNewCode, String platform) throws Exception {
        String url = "https://c.y.qq.com/v8/fcg-bin/v8.fcg";
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "channel",
                "page",
                "key",
                "pagesize",
                "pagenum",
                "hostUin",
                "needNewCode",
                "platform",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                channel,
                page,
                key,
                pagesize,
                pagenum,
                hostUin,
                needNewCode,
                platform,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, null);
    }

    /**
     * 获取qq音乐，音乐播放的vkey
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getSongPurl")
    public Object getSongPurl(@RequestBody String data) throws Exception {
        String url = "https://u.y.qq.com/cgi-bin/musicu.fcg";
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
                parse.get("loginUin"),
                parse.get("hostUin"),
                parse.get("platform"),
                parse.get("needNewCode"),
                parse.get("data"),
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, header);
    }

    /**
     * 获取qq音乐歌手详情
     *
     * @return 返回结果为json数据
     */
    @PostMapping("/getSingerDetail")
    public Object getSingerDetail(String hostUin, String needNewCode, String platform, String order, String begin, String num, String songstatus, String singermid) throws Exception {
        String url = "https://c.y.qq.com/v8/fcg-bin/fcg_v8_singer_track_cp.fcg";
        Object[] params = new Object[]{
                BaseParamsAndValues.G_TK,
                BaseParamsAndValues.IN_CHAR_SET,
                BaseParamsAndValues.OUT_CHAR_SET,
                BaseParamsAndValues.FORMAT,
                BaseParamsAndValues.NOTICE,
                "hostUin",
                "needNewCode",
                "platform",
                "order",
                "begin",
                "num",
                "songstatus",
                "singermid",
        };
        Object[] values = new Object[]{
                BaseParamsAndValues.G_TK_VALUE,
                BaseParamsAndValues.IN_CHAR_SET_VALUE,
                BaseParamsAndValues.OUT_CHAR_SET_VALUE,
                BaseParamsAndValues.FORMAT_VALUE,
                BaseParamsAndValues.NOTICE_VALUE,
                hostUin,
                needNewCode,
                platform,
                order,
                begin,
                num,
                songstatus,
                singermid,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, null);
    }

    /**
     * 获取qq音乐歌单歌曲详情
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getCDInfo")
    public Object getCDInfo(String type, String json, String utf8, String onlysong, String new_format, String disstid, String loginUin, String hostUin, String platform, String needNewCode) throws Exception {
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
                type,
                json,
                utf8,
                onlysong,
                new_format,
                disstid,
                loginUin,
                hostUin,
                platform,
                needNewCode,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, header);
    }

    /**
     * 获取qq音乐歌词
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getLyric")
    public Object getLyric(String songmid, String platform, String hostUin, String needNewCode, String categoryId, String pcachetime) throws Exception {
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
                platform,
                hostUin,
                needNewCode,
                categoryId,
                pcachetime,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, header);
    }

    /**
     * 获取qq音乐排行榜
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getTopList")
    public Object getTopList(String uin, String needNewCode, String platform) throws Exception {
        String url = "https://c.y.qq.com/v8/fcg-bin/fcg_myqq_toplist.fcg";
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
                uin,
                needNewCode,
                platform,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, null);
    }

    /**
     * 获取排行榜歌曲列表
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getTopMusicList")
    public Object getTopMusicList(String topid, String needNewCode, String uin, String tpl, String page, String type, String platform) throws Exception {
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
                needNewCode,
                uin,
                tpl,
                page,
                type,
                platform,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, null);
    }

    /**
     * 获取热门搜索关键词
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getHotKey")
    public Object getHotKey(String uin, String needNewCode, String platform) throws Exception {
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
                uin,
                needNewCode,
                platform,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, null);
    }

    /**
     * 搜索
     *
     * @return 返回结果为json数据
     */
    @PostMapping("/search")
    public Object search(String w, String p, String perpage, String n, String catZhida, String zhidaqu, String t, String flag, String ie, String sem, String remoteplace, String needNewCode, String platform, String uin) throws Exception {
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
                zhidaqu,
                t,
                flag,
                ie,
                sem,
                remoteplace,
                needNewCode,
                platform,
                uin,
        };
        List<NameValuePair> paramsList = HttpDelegate.getParams(params, values);
        return HttpDelegate.sendGet(url, paramsList, null);
    }
}
