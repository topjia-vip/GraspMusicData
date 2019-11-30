package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.Singer;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 19:54
 */
public interface SingerListService {
    List<Singer> getSingerList() throws Exception;
}
