package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.Song;
import com.topjia.getqqyydata.service.SongPurlService;
import com.topjia.getqqyydata.service.TopMusicListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wjh
 * @date 2019-11-30 22:07
 */
@CrossOrigin
@RestController
public class TopMusicListController {
    @Autowired
    private TopMusicListService topMusicListService;

    @Autowired
    private SongPurlService songPurlService;

    /**
     * 获取排行榜歌曲列表
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getTopMusicList")
    public Object getTopMusicList(String topid) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Song> songs = topMusicListService.getTopMusicList(topid);
        songs = songPurlService.getSongPurl(songs);
        result.put("code", 0);
        result.put("data", songs);
        return result;
    }
}
