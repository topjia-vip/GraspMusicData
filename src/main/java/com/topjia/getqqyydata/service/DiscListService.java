package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.DiscList;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 19:39
 */
public interface DiscListService {
    List<DiscList> getDiscList(String sortId) throws Exception;
}
