package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.Lyric;
import com.topjia.getqqyydata.service.LyricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取qq音乐歌词
 *
 * @author wjh
 * @date 2019-11-30 21:51
 */
@CrossOrigin
@RestController
public class LyricController {
    @Autowired
    private LyricService lyricService;

    /**
     * 获取qq音乐歌词
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getLyric")
    public Object getLyric(String songmid) throws Exception {
        Map<String, Object> result = new HashMap<>();
        Lyric lyric = lyricService.getLyric(songmid);
        result.put("code", 0);
        result.put("data", lyric);
        return result;
    }
}
