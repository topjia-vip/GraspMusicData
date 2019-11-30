package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.HotKey;
import com.topjia.getqqyydata.service.HotKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author wjh
 * @date 2019-11-30 22:15
 */
@CrossOrigin
@RestController
public class HotKeyController {
    @Autowired
    private HotKeyService hotKeyService;

    /**
     * 获取热门搜索关键词
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getHotKey")
    public Object getHotKey() throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<HotKey> hotKeys = hotKeyService.getHotKey();
        result.put("code", 0);
        result.put("data", hotKeys);
        return result;
    }
}
