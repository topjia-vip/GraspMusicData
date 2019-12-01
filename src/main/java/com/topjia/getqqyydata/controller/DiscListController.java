package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.DiscList;
import com.topjia.getqqyydata.service.DiscListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 获取qq音乐的推荐歌单列表的controller
 *
 * @author wjh
 * @date 2019-11-30 19:25
 */
@CrossOrigin
@RestController
public class DiscListController {
    @Autowired
    private DiscListService discListService;

    /**
     * 获取qq音乐的推荐歌单列表
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getDiscList")
    public Object getDiscList(String sortId) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        List<DiscList> discLists = discListService.getDiscList(sortId);
        result.put("code", 0);
        result.put("list", discLists);
        return result;
    }
}
