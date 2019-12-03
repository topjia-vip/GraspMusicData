package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.Song;
import com.topjia.getqqyydata.service.SearchService;
import com.topjia.getqqyydata.service.SongPurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wjh
 * @date 2019-11-30 22:23
 */
@CrossOrigin
@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private SongPurlService songPurlService;

    /**
     * 搜索功能
     *
     * @return 返回结果为json数据
     */
    @PostMapping("/search")
    public Object search(String w, String p, String n, String catZhida) throws Exception {
        Map<String, Object> result = new HashMap<>();
        HashMap<String, Object> searchRes = searchService.search(w, p, n, catZhida);
        List<Song> songs = (List<Song>) searchRes.get("songs");
        songs = songPurlService.getSongPurl(songs, "");
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(searchRes.get("searchResult"));
        objects.add(songs);
        result.put("code", 0);
        result.put("data", objects);
        return result;
    }
}
