package com.topjia.getqqyydata.controller;

import com.topjia.getqqyydata.entity.RecommendPic;
import com.topjia.getqqyydata.service.RecommendPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 获取qq音乐推荐图片的controller
 *
 * @author wjh
 * @date 2019-11-30 19:15
 */
@CrossOrigin
@RestController
public class RecommendPicController {
    @Autowired
    private RecommendPicService recommendPicService;

    /**
     * 获取qq音乐的推荐图片slider
     *
     * @return 返回结果为json数据
     */
    @PostMapping(value = "/getRecommend")
    public Object getRecommend() throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        List<RecommendPic> picList = recommendPicService.getRecommend();
        result.put("code", 0);
        result.put("data", picList);
        return result;
    }
}
