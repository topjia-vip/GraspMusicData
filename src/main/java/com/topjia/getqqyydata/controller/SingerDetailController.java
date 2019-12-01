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
        List<Object> list = singerDetailService.getSingerDetail(singermid);
        if ((boolean) list.get(1)) {
            result.put("code", 0);
            result.put("data", list.get(0));
            return result;
        } else {
            List<Song> songs = songPurlService.getSongPurl((List<Song>) list.get(0), singermid);
            result.put("code", 0);
            result.put("data", songs);
            return result;
        }
    }
}
