package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.service.SongPurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author wjh
 * @date 2019-12-03 20:28
 */
@CrossOrigin
@RestController
public class SongPurlController {
    @Autowired
    private SongPurlService songPurlService;

    @PostMapping("/getSongPlayVkey")
    public Object getSongPlayVkey(String songmids) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        List<String> playUrls = songPurlService.getSongPlayVkey(songmids);
        result.put("code", 0);
        result.put("data", playUrls);
        return result;
    }

}
