package com.topjia.getqqyydata.controller;

import com.alibaba.fastjson.JSONArray;
import com.topjia.getqqyydata.service.TopListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author wjh
 * @date 2019-11-30 21:58
 */
@CrossOrigin
@RestController
public class TopListController {

    @Autowired
    private TopListService topListService;

    /**
     * 获取qq音乐排行榜
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getTopList")
    public Object getTopList() throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        JSONArray topList = topListService.getTopList();
        result.put("code", 0);
        result.put("topList", topList);
        return result;
    }
}
