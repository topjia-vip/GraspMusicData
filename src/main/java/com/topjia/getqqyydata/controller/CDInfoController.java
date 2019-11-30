package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.Song;
import com.topjia.getqqyydata.service.CDInfoService;
import com.topjia.getqqyydata.service.SongPurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 获取qq音乐歌单歌曲详情
 *
 * @author wjh
 * @date 2019-11-30 21:21
 */
@CrossOrigin
@RestController
public class CDInfoController {
    @Autowired
    private CDInfoService cdInfoService;

    @Autowired
    private SongPurlService songPurlService;

    /**
     * 获取qq音乐歌单歌曲详情
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getCDInfo")
    public Object getCDInfo(String disstid) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Song> songs = cdInfoService.getCDInfo(disstid);
        songs = songPurlService.getSongPurl(songs);
        result.put("code", 0);
        result.put("data", songs);
        return result;
    }
}
