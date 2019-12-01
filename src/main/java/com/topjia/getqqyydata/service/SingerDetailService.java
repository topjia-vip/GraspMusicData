package com.topjia.getqqyydata.service;

import com.topjia.getqqyydata.entity.Song;

import java.util.List;

/**
 * @author wjh
 * @date 2019-11-30 20:26
 */
public interface SingerDetailService {
    List<Object> getSingerDetail(String singermid) throws Exception;
}
