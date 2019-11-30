package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.RecommendPic;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 19:44
 */
public interface RecommendPicService {
    List<RecommendPic> getRecommend() throws Exception;
}
