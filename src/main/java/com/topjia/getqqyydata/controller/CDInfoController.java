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
        List<Object> list = cdInfoService.getCDInfo(disstid);
        if ((boolean) list.get(1)) {
            result.put("code", 0);
            result.put("data", list.get(0));
            return result;
        } else {
            List<Song> songs = songPurlService.getSongPurl((List<Song>) list.get(0), disstid);
            result.put("code", 0);
            result.put("data", songs);
            return result;
        }
    }
}
