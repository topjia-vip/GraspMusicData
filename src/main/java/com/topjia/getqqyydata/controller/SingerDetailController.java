package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.Song;
import com.topjia.getqqyydata.service.SingerDetailService;
import com.topjia.getqqyydata.service.SongPurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wjh
 * @date 2019-11-30 20:26
 */
@CrossOrigin
@RestController
public class SingerDetailController {
    @Autowired
    private SingerDetailService singerDetailService;

    @Autowired
    private SongPurlService songPurlService;

    /**
     * 获取qq音乐歌手详情
     *
     * @return 返回结果为json数据
     */
    @PostMapping("/getSingerDetail")
    public Object getSingerDetail(String singermid) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Song> songs = singerDetailService.getSingerDetail(singermid);
        songs = songPurlService.getSongPurl(songs);
        result.put("code", 0);
        result.put("data", songs);
        return result;
    }
}
