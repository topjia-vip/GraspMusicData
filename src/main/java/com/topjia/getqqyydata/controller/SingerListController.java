package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.Singer;
import com.topjia.getqqyydata.service.SingerListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 获取歌手列表的controller
 *
 * @author wjh
 * @date 2019-11-30 19:53
 */
@CrossOrigin
@RestController
public class SingerListController {
    @Autowired
    private SingerListService singerListService;

    /**
     * 获取qq音乐歌手列表
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getSingerList")
    public Object getSingerList() throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Singer> singers = singerListService.getSingerList();
        result.put("code", 0);
        result.put("data", singers);
        return result;
    }
}
