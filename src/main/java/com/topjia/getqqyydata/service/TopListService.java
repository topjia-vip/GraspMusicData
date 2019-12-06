package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.TopGroup;

import java.util.ArrayList;

/**
 * @author wjh
 * @date 2019-11-30 21:59
 */
public interface TopListService {
    ArrayList<TopGroup> getTopList() throws Exception;
}
